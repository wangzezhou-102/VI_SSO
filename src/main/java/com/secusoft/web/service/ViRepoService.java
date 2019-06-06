package com.secusoft.web.Service;

import com.secusoft.web.model.ResultVo;
import com.secusoft.web.model.ViRepoBean;
import com.secusoft.web.model.ViRepoVo;

/**
 * @author chjiang
 * @since 2019/6/6 14:13
 */
public interface ViRepoService {

    ResultVo insertViRepo(ViRepoBean viRepoBean);

    ResultVo updateViRepo(ViRepoBean viRepoBean);

    ResultVo delViRepo(Integer id);

    ResultVo getAllViRepo(ViRepoVo viRepoVo);
}
