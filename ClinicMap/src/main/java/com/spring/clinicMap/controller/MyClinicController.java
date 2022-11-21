package com.spring.clinicMap.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.clinicMap.entity.Member;
import com.spring.clinicMap.entity.MyClinic;
import com.spring.clinicMap.entity.MyClinicId;
import com.spring.clinicMap.service.MyClinicService;

@RestController
public class MyClinicController {
	
	@Autowired
	MyClinicService myClinicService;
	
	@PostMapping("/submit")
	public ResponseEntity<?> submitClinic(@RequestBody MyClinic myclinic, @AuthenticationPrincipal String userId){
		try {
			Member member = new Member();
			member.setUserId(userId);
			System.out.println(myclinic.getYadmNm());
			
			myclinic.setMember(member);
			MyClinic saveClinic = myClinicService.submitClinic(myclinic);
			
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("saveClinic", saveClinic);
			return ResponseEntity.ok().body(response);
			
		}catch(Exception e) {
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(errorMap);
		}
		
	}
	
	@GetMapping("/getMyClinicList")
	public ResponseEntity<?> getMyClinicList(@AuthenticationPrincipal String userId, @PageableDefault(page = 0, size = 4, direction = Direction.DESC) Pageable pageable){
		try {
			Member member = new Member();
			member.setUserId(userId);
			
			Map<String, Object> response = new HashMap<String, Object>();
			Page<MyClinic> myClinicList = myClinicService.getMyClinicList(member, pageable);
			
			response.put("myClinicList", myClinicList);
			
			return ResponseEntity.ok().body(response);
			
		}catch(Exception e) {
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(errorMap);
		}
	}
	
	
	
	
	@PostMapping("/delete")
	public ResponseEntity<?> deleteMyClinic(@RequestBody MyClinic myclinic, @AuthenticationPrincipal String userId) {
		try {
			System.out.println(myclinic.getYkiho());
			MyClinicId myClinicId = new MyClinicId();
			myClinicId.setMember(userId);
			myClinicId.setYkiho(myclinic.getYkiho());
			
			myClinicService.deleteClinic(myClinicId);
			
			Map<String, Object> response = new HashMap<String, Object>();
			return ResponseEntity.ok().body(response);
			
		}catch(Exception e) {
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(errorMap);
		}
	}
	
	
	
	
	
	
	
	
	


}
