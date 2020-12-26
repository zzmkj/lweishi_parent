package com.lweishi.service;

import com.lweishi.constant.Constant;
import com.lweishi.dto.ActivityDTO;
import com.lweishi.dto.BrandDTO;
import com.lweishi.exception.GlobalException;
import com.lweishi.exception.http.NotFoundException;
import com.lweishi.model.Activity;
import com.lweishi.model.Brand;
import com.lweishi.repository.ActivityRepository;
import com.lweishi.repository.BrandRepository;
import com.lweishi.utils.BeanNullUtil;
import com.lweishi.utils.IDUtil;
import com.lweishi.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author geek
 * @CreateTime 2020/12/3 23:49
 * @Description 活动业务
 */
@Service
@Transactional
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return activityRepository.findAll(sort);
    }

    public Page<Activity> findAll(Pageable pageable, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return activityRepository.findAll(pageable);
        }
        return activityRepository.findByTitleLike("%" + keyword + "%", pageable);
    }

    public Activity findById(Long id) {
        return activityRepository.findById(id).orElseThrow(() -> new GlobalException(ResultCode.ERROR, "该品牌信息不存在"));
    }

    public void deleteById(Long id) {
        activityRepository.deleteById(id);
    }

    public Activity save(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDTO, activity);
        activity.setOnline(true);
        return activityRepository.save(activity);
    }

    public Activity update(ActivityDTO activityDTO) {
        if (activityDTO.getId() == null) {
            throw new GlobalException(ResultCode.ERROR, "更新活动信息失败");
        }
        Activity activity = findById(activityDTO.getId());
        BeanUtils.copyProperties(activityDTO, activity, BeanNullUtil.getNullPropertyNames(activityDTO));
        return activityRepository.save(activity);
    }

    public List<Activity> findByName(String name) {
        return activityRepository.findByNameAndOnline(name, Constant.ACTIVITY_VALID);
    }

    public Activity getByName(String name) {
        return activityRepository.findByName(name).orElseThrow(() -> new NotFoundException(40001));
    }

    public void changeOnline(Long id, Boolean online) {
        Activity activity = findById(id);
        activity.setOnline(online);
        activityRepository.save(activity);
    }
}
