package nl.tudelft.app.frontend.ctrl;

import nl.tudelft.app.frontend.data.LibVersionDTO;
import nl.tudelft.libversion.util.VersionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/libversion")
public class LibVersionController {
    @GetMapping
    public ResponseEntity<LibVersionDTO> getLibVersionInfo() {
        return ResponseEntity.ok(new LibVersionDTO(VersionUtil.VERSION, VersionUtil.BRANCH, VersionUtil.SHORT_COMMIT_HASH, VersionUtil.COMMIT_HASH));
    }
}
