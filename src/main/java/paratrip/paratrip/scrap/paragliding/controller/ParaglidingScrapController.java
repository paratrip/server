package paratrip.paratrip.scrap.paragliding.controller;

import static paratrip.paratrip.scrap.paragliding.service.dto.request.ParaglidingScrapRequestDto.*;
import static paratrip.paratrip.scrap.paragliding.service.dto.response.ParaglidingScrapResponseDto.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.scrap.paragliding.service.ParaglidingScrapService;

@RestController
@RequestMapping("/paragliding/scrap")
@RequiredArgsConstructor
@Tag(name = "패러글라이딩 스크랩 API", description = "담당자(박종훈)")
public class ParaglidingScrapController {
	private final ParaglidingScrapService paraglidingScrapService;

	@PostMapping()
	public ResponseEntity<BaseResponse> saveParaglidingScrap(
		@RequestBody SaveParaglidingScrapRequestDto request
	) {
		SaveParaglidingScrapResponseDto response = paraglidingScrapService.saveParaglidingScrap(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@DeleteMapping
	public ResponseEntity<BaseResponse> deleteParaglidingScrap(
		@RequestBody DeleteParaglidingScrapRequestDto request
	) {
		paraglidingScrapService.deleteParaglidingScrap(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@GetMapping()
	public ResponseEntity<BaseResponse<List<GetParaglidingScrapResponseDto>>> getParaglidingScrap(
		@RequestParam(value = "memberSeq") Long memberSeq
	) {
		List<GetParaglidingScrapResponseDto> response = paraglidingScrapService.getParaglidingScrap(memberSeq);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}
}
