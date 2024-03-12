package com.zjw.setter.impl;

import com.zjw.setter.IAccountServiceC;
import com.zjw.setter.IAccountServiceD;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author 朱俊伟
 * @since 2024/03/12 10:35
 */
@Setter
@Service("accountServiceD")
public class IAccountServiceDImpl implements IAccountServiceD {

    private IAccountServiceC accountServiceC;

}
