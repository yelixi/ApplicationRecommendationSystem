package com.example.demo.controller;

import com.example.demo.entity.Feedback;
import com.example.demo.entity.User;
import com.example.demo.model.RestResult;
import com.example.demo.model.UserInformation;
import com.example.demo.service.FeedbackService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 林夕
 * Date 2021/4/27 23:12
 */
@RequestMapping("/feedback")
@RestController
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping("/addFeedback")
    public RestResult<Boolean> addFeedback(@RequestBody Feedback feedback/* ,
                                           Authentication authentication*/){
//        UserInformation u = (UserInformation)authentication.getPrincipal();
        User user = new User();
        user.setId(1);
        UserInformation u = new UserInformation(user);
        return RestResult.success(feedbackService.add(feedback,u));
    }
}
