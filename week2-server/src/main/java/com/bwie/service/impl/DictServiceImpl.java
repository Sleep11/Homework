package com.bwie.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.domain.Dict;
import com.bwie.mapper.DictMapper;
import com.bwie.service.DictService;
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService{

}
