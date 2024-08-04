package paratrip.paratrip.board.domain;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoardDomain {
	public boolean checkImages(List<MultipartFile> images) {
		return images != null;
	}
}
