package com.scuse.volunteerhub.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scuse.volunteerhub.entity.Model;
import com.scuse.volunteerhub.mapper.ModelMapper;
import com.scuse.volunteerhub.service.ModelService;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper,Model> implements ModelService {
}
