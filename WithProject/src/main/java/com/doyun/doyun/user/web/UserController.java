package com.doyun.doyun.user.web;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.doyun.doyun.files.web.FileUtils;
import com.doyun.doyun.user.dto.UserKey;
import com.doyun.doyun.user.dto.UserVO;
import com.doyun.doyun.user.service.UserService;

@Controller
@EnableWebSecurity(debug = true)
//@SessionAttributes("loginVO")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	FileUtils fileSave;
	
	@Inject
	BCryptPasswordEncoder pwd;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/user/signInUser")
	public String addUSer(@RequestParam Map paramMap,Model model) {
		paramMap.put("userPwd",pwd.encode(paramMap.get("userPwd").toString()));
		paramMap.put("phone",paramMap.get("phone_1").toString()+paramMap.get("phone_2").toString());
		int result=userService.addUser(paramMap);
		if(result>0) {
			model.addAttribute("url","/prepared");
			model.addAttribute("message","회원가입 완료.");
		}
		return "common/message";
	}
	
	@RequestMapping("/user/login")
	public String loginUser(@RequestParam Map paramMap,Model model,HttpSession session) {
		//추후 jpa연동시 사용할 메소드
//		UserVO user=userService.selectUserLoginInfo(paramMap);
		UserVO user=userService.selectUserInfo(paramMap);
		if(user==null) {
			model.addAttribute("message","존재하지 않는 계정입니다. 회원가입 후 이용하실 수 있습니다.");
			model.addAttribute("url","/user/signIn");
			return "common/message";
		}
		boolean bool=pwd.matches(paramMap.get("password").toString(), user.getPassword());
		if(!bool) {
			model.addAttribute("message","패스워드가 맞지 않습니다. 다시 시도해주세요.");
			model.addAttribute("url","/prepared");
			return "common/message";
		}
		//세션 처리. jsp단에서 sessionScope로 불러올 수 있게 한다.
		session.setAttribute("loginVO",user);
		
		//세션에서는 userSeq로 유저를 특정한다.
		//view단에서는 userSeq를 감추기 위해 EncryptUserVO를 사용하여 제어한다.
		//chat으로 넘어가면서 암호화된 userseq를 해독하여 사용한다.
		//암호화 할 seq는 앞에 특정 단어를 붙인다. 기본적으로 "user_key"라는 단어를 추가하여 붙이고 복호화 한 후 확인하는 방식을 채택한다.
		String userKey="user_key"+user.getUserSeq();
		String key=pwd.encode(userKey);
		key=key.replace("/", "$$$");
		UserKey userKeyInfo=new UserKey(user.getUserSeq(),key);
		session.setAttribute("userKey",userKeyInfo);
		return "index/prepared";
	}
	
	@ResponseBody
	@RequestMapping("/user/checkId")
	public JSONObject checkUserId(@RequestParam Map paramMap) {
		JSONObject json=new JSONObject();
		String id=userService.selectCheckId(paramMap);
		if(id==null) {
			json.put("result", true);
		}else {
			json.put("result",false);
		}
		return json;
	}
	
	@RequestMapping("/user/myInfoView")
	public String myInfoView(@RequestParam Map paramMap,Model model,HttpSession session) {
		UserKey userKey=(UserKey) session.getAttribute("userKey");
		model.addAttribute("backUrl","/user/friendList/"+userKey.getUserKey());
		return "user/myInfo";
	}
	
	@RequestMapping("/user/userInfo")
	public String myInfoRegisterView(HttpServletRequest request,Model model,Map paramMap) {
		UserVO loginVO=(UserVO) request.getSession().getAttribute("loginVO");
		paramMap.put("userId", loginVO.getUserId());
		UserVO user=userService.selectUserInfo(paramMap);
		request.getSession().setAttribute("loginVO", user);
		model.addAttribute("backUrl","/user/myInfoView");
		return "user/myInfoRegister";
	}
	
	@ResponseBody
	@RequestMapping("/user/userPhotoEdit")
	public HashMap<String,String> updateUserProfileFile(HttpServletRequest request,HttpSession session) throws IOException {
		String pathName="userProfile";
		MultipartHttpServletRequest multi=(MultipartHttpServletRequest) request;
		MultipartFile file=multi.getFile("profileImg");
		
		
		int fileSeq=fileSave.uploadFile(request,file,pathName);
		UserVO loginVO=(UserVO) request.getSession().getAttribute("loginVO");
		loginVO.setImageUrl(String.valueOf(fileSeq));
		//fileSeq를 업데이트 한다.
		userService.updateUserProfileImg(loginVO);
		Map param=new HashMap<String,String>();
		param.put("userId",loginVO.getUserId());
		loginVO=userService.selectUserInfo(param);
		session.removeAttribute("loginVO");
		session.setAttribute("loginVO", loginVO);
		HashMap<String, String> savePath=userService.selectFilePath(loginVO.getUserSeq());
		
		return savePath;
	}
	
	@RequestMapping("/user/userInfoUpdate")
	public String updateUserInfo(@RequestParam Map paramMap,Model model,HttpServletRequest request,HttpSession session) {
		userService.updateUserProfileInfo(paramMap);
		String userId=userService.selectUserId(paramMap);
		paramMap.put("userId",userId);
		UserVO user=userService.selectUserInfo(paramMap);
		model.addAttribute("backUrl","/user/myInfoView");
		session.removeAttribute("loginVO");
		session.setAttribute("loginVO", user);
		user=(UserVO) session.getAttribute("loginVO");
		return "user/myInfoRegister";
	}
	
	@RequestMapping("/user/findFriend")
	public String findFriendView(@RequestParam Map param,Model model) {
		return "user/findFriendView";
	}
	
	@ResponseBody
	@RequestMapping("/user/findFriendById")
	public UserVO findFriendById(Model model,@RequestParam Map param) {
		 UserVO userVO=userService.selectUserInfo(param);
		return userVO;
	}
	
	@RequestMapping("/user/addFriend")
	public String addFrined(@RequestParam Map param,HttpSession session) {
		UserVO user=(UserVO) session.getAttribute("loginVO");
		param.put("userSeq",user.getUserSeq());
		System.out.println(param);
		userService.insertUserFriend(param);
		UserKey userKey=(UserKey) session.getAttribute("userKey");
		return "redirect:/user/friendList/"+userKey.getUserKey();
	}
	
	
}
