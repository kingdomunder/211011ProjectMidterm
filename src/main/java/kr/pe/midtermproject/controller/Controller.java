package kr.pe.midtermproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.midtermproject.model.BoardService;
import kr.pe.midtermproject.model.JWT;
import kr.pe.midtermproject.model.NoticeService;
import kr.pe.midtermproject.model.TicketService;
import kr.pe.midtermproject.model.UsersService;
import kr.pe.midtermproject.model.domain.Board;
import kr.pe.midtermproject.model.domain.Notice;
import kr.pe.midtermproject.model.domain.Users;
import kr.pe.midtermproject.model.dto.BoardDTO;
import kr.pe.midtermproject.model.dto.BoardResDTO;
import kr.pe.midtermproject.model.dto.NoticeDTO;
import kr.pe.midtermproject.model.dto.NoticeResDTO;


@RestController
public class Controller {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private NoticeService noticeService;

	//회원가입
	@PostMapping("join")
	public boolean createUser(@RequestBody Users user) {
		boolean check = userService.checkedUserId(user.getUserId());
		boolean result = false;
		
		if(!check) {
			result = userService.createUser(user);
			System.out.println(result);
			result = true;
		}
		
		return result;
	}

	//로그인
	@PostMapping("login")
	public String login(@RequestBody Users reqUser) {
		Users user = userService.login(reqUser.getUserId(), reqUser.getUserPw());
		System.out.println(user);
		String token = null; 
		if (user != null) {
			JWT jwt = new JWT();
			token = jwt.createToken(user);
	        System.out.println(token);
		}
		
		return token;
	}

	@GetMapping("user/{userIdx}")
	public Users userInfo(@PathVariable Long userIdx) {
		Users result = null;
		result = userService.getUser(userIdx);
		return result;
	}
	
	// userId중복확인
	@PostMapping("checkedid")
	public boolean checkedUserId(@RequestBody String userId){
		boolean result = userService.checkedUserId(userId);
		
		return result;
	}
	
	//userId로 정보수정
	@PutMapping("user/{user_idx}")
	public void updateUser(@PathVariable Long user_idx, @RequestBody Users reqUser) {
		boolean result = userService.updateUser(user_idx, reqUser);
		System.out.println(result);
	}
	
	//userId로 삭제하기
	@DeleteMapping("user/{user_idx}")
	public void deleteUser(@PathVariable Long user_idx) {
		boolean result = userService.deleteUser(user_idx);
		System.out.println(result);
	}
	
	//post 작성
	@PostMapping("board")
	public Board createPost(@RequestBody BoardDTO board) {		
		
		return boardService.createBoard(board);
	}
	
	//post 수정
	@PutMapping("board/{id}")
	public Long updatePost(@PathVariable Long id, @RequestBody BoardDTO board) {
		
		return boardService.updateBoard(id, board);
	}
	
	 //post 개별 조회
    @GetMapping("board/{id}")
    public BoardResDTO searchByPostId(@PathVariable Long id) {
    	
        return boardService.searchByPostId(id);
    }
    
    //post 전체 조회(목록)
    @GetMapping("board")
    public List<BoardResDTO> searchAllPostDesc() {
    	
        return boardService.searchAllDesc();
    }
    
    //post 삭제
    @DeleteMapping("board/{id}")
    public void deletePost(@PathVariable Long id){
    	
        boardService.deletePost(id);
    }

    //notice 작성
  	@PostMapping("notice")
  	public Notice createNotice(@RequestBody NoticeDTO notice) {		
  		
  		return noticeService.createNotice(notice);
  	}
  	
  	//notice 수정
  	@PutMapping("notice/{id}")
	public Notice updateNotice(@PathVariable Long id, @RequestBody NoticeDTO notice) {
  		
		return noticeService.updateNotice(id, notice);
	}
  	
  	//notice 1개 조회
  	@GetMapping("notice/{id}")
    public NoticeResDTO searchByNoticeId(@PathVariable Long id) {
    	
        return noticeService.searchByNoticeId(id);
    }
  	
  	//notice 전체 조회(목록)
  	@GetMapping("notice")
    public List<NoticeResDTO> searchAllNoticeDesc() {
    	
        return noticeService.searchAllNoticeDesc();
    }
  	
  	//notice 삭제
    @DeleteMapping("notice/{id}")
    public void deleteNotice(@PathVariable Long id){
    	
    	noticeService.createNotice(id);
    }
}
