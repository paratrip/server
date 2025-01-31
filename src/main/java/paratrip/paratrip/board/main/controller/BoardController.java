package paratrip.paratrip.board.main.controller;

import static paratrip.paratrip.board.main.controller.vo.request.BoardRequestVo.*;
import static paratrip.paratrip.board.main.controller.vo.response.BoardResponseVo.*;
import static paratrip.paratrip.board.main.service.dto.request.BoardRequestDto.*;
import static paratrip.paratrip.board.main.service.dto.response.BoardResponseDto.*;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.service.BoardService;
import paratrip.paratrip.board.main.validates.GetBoardValidator;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "커뮤니티 API", description = "담당자(박종훈)")
public class BoardController {
	private final BoardService boardService;

	@PostMapping(name = "게시물 생성")
	@Operation(summary = "게시물 생성 API", description = "제목/내용 필수, 지역/이미지 선택")
	public ResponseEntity<BaseResponse<AddBoardResponse>> saveBoard(
		@Valid
		@ModelAttribute AddBoardRequest request
	) throws IOException {
		AddBoardResponseDto addBoardResponseDto = boardService.saveBoard(request.toAddBoardRequestDto());
		AddBoardResponse response = addBoardResponseDto.toAddBoardResponse();

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@PutMapping(name = "게시물 수정")
	@Operation(summary = "게시물 수정 API", description = "제목/내용 필수, 지역/이미지 선택")
	public ResponseEntity<BaseResponse> modifyBoard(
		@Valid
		@ModelAttribute ModifyBoardRequest request
	) throws IOException {
		boardService.modifyBoard(request.toModifyBoardRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@GetMapping(value = "all", name = "전체 게시물 조회")
	@Operation(summary = "전체 게시물 조회 API", description = "전체 게시물 조회")
	public ResponseEntity<BaseResponse<Page<GetAllBoardResponseDto>>> getAllBoard(
		@Valid
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<GetAllBoardResponseDto> response = boardService.getAllBoard(pageable);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@GetMapping(name = "게시물 조회")
	@Operation(summary = "게시물 조회 API", description = "게시물 조회")
	public ResponseEntity<BaseResponse<GetBoardResponseDto>> getBoard(
		@Valid
		@RequestParam(value = "boardSeq", defaultValue = "1") Long boardSeq,
		@RequestParam(value = "memberSeq", defaultValue = "-1") Long memberSeq
	) {
		GetBoardResponseDto response = boardService.getBoard(boardSeq, memberSeq);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@GetMapping(value = "popularity", name = "이번주 인기 게시물")
	@Operation(summary = "이번주 인기 게시물 API", description = "이번주 인기 게시물")
	public ResponseEntity<BaseResponse<List<GetAllBoardResponseDto>>> getPopularityBoard(
		@Valid
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		List<GetAllBoardResponseDto> response = boardService.getPopularityBoard(pageable);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@GetMapping(value = "my", name = "내가 쓴 게시물")
	@Operation(summary = "내가 쓴 게시물 API", description = "내가 쓴 게시물")
	public ResponseEntity<BaseResponse<Page<GetAllBoardResponseDto>>> myBoard(
		@Valid
		@RequestParam(value = "memberSeq", defaultValue = "0") Long memberSeq,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<GetAllBoardResponseDto> response = boardService.myBoard(memberSeq, pageable);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@DeleteMapping()
	@Operation(summary = "게시물 삭제 API", description = "게시물 삭제 API")
	public ResponseEntity<BaseResponse> deleteBoard(
		@Valid
		@RequestBody DeleteBoardRequestDto request
	) {
		boardService.deleteBoard(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}
}
