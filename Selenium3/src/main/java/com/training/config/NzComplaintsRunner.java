//package com.training.appConfig;
//
//import com.training.model.Binder;
//import com.training.service.NzipotmService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NzComplaintsRunner implements CommandLineRunner {
//
//    @Autowired
//    private NzipotmService nzComplaintRobotService;
//
//    @Override
//    public void run(String... args) {
//        Binder binder = nzComplaintRobotService.runComplaintsRobot();
//        if (binder != null) {
//            System.out.println("NZ Complaint Robot executed successfully.");
//        } else {
//            System.out.println("Failed to run NZ Complaint Robot.");
//        }
//    }
//}
