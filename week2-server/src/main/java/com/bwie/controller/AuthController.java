package com.bwie.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.bwie.domain.Menu;
import com.bwie.domain.User;
import com.bwie.service.MenuService;
import com.bwie.service.UserService;
import com.bwie.utils.JWTUtils;
import com.bwie.utils.PictureCaptchaUtil;
import com.bwie.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyuxiang
 * @description: TODO
 * @since 2026/5/8 10:05:32
 */

@Api(tags = "登录")
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    @Autowired
    HttpServletResponse response;

    @Autowired
    RedisTemplate redisTemplate;

  @GetMapping("getImageCode/{uuid}")
  public void getImageCode(@PathVariable String uuid) throws IOException {
      String randomCaptcha = PictureCaptchaUtil.getRandomCaptcha(response);
      redisTemplate.opsForValue().set(uuid,randomCaptcha,1,TimeUnit.MINUTES);
  }

    @PostMapping("login")
    public R login(@RequestBody @Validated User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User realUser =userService.getOne(queryWrapper);
        if (ObjectUtils.isEmpty(realUser)){
            return R.error().message("该用户不存在");
        }
        if (!user.getPassword().equals(realUser.getPassword())){
            return R.error().message("密码错误");
        }

        redisTemplate.opsForValue().set("user:"+realUser.getUsername(),realUser);


        String token = JWTUtils.createToken(realUser.getId().toString(), 30);
        return R.ok().data("token",token);
    }

    @GetMapping("checkImageCode/{imageCode}/{uuid}")
    public R checkImageCode(@PathVariable String imageCode,@PathVariable String uuid){

        String vercode= (String) redisTemplate.opsForValue().get(uuid);

        if (ObjectUtils.isEmpty(vercode)){
            return R.error().message("验证码已过期");
        }

        if (!imageCode.equals(vercode)){
            return R.error().message("验证码错误");
        }

        return R.ok();
    }

    @GetMapping("getMenusByToken")
    public R getMenusByToken(@RequestHeader("token")String token){

        String userId = JWTUtils.getUserId(token);

        List<Menu> menuList = menuService.myList(userId);

        redisTemplate.opsForValue().set("menu",menuList);

        redisTemplate.opsForList().leftPush("menuList",menuList);

        List list = redisTemplate.opsForList().range("menuList", 0, -1);

        for (Object o : list) {
            System.out.println(o);
        }

        System.out.println("planA");


        List<Menu> menus = (List<Menu>) redisTemplate.opsForValue().get("menu");

        for (Menu menu : menus) {
            System.out.println(menu);
        }


        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();


        List<Tree<String>> treeNodes = TreeUtil.<Menu, String>build(menuList, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId().toString());
                    tree.setParentId(treeNode.getParentId().toString());

                    tree.setName(treeNode.getName());
                    // 扩展属性 ...
                    tree.putExtra("path", treeNode.getPath());

                });

        return R.ok().data("menus",treeNodes);
    }
}