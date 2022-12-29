package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.CollectParam;
import org.aliang.pojo.Collect;
import org.aliang.utils.R;

public interface CollectService extends IService<Collect> {

    /**
     * 保存收藏
     * @return
     */
    R saveCollect(CollectParam collectParam);
}
