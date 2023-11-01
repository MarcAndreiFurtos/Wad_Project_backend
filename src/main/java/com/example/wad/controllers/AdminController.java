package com.example.wad.controllers;

import com.example.wad.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping(params = "userName")
    public ResponseEntity<String> BanUser(@RequestParam String userName){
        adminService.BanUser(userName);
        return new ResponseEntity<>("user banned successfully",OK);
    }
@GetMapping(params = "postId")
    public ResponseEntity<String> DeletePost(@RequestParam Long postId){
        adminService.DeletePost(postId);
        return new ResponseEntity<>("post deleted successfully", OK);
    }
}
