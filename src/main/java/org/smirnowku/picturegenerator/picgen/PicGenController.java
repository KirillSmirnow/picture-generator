package org.smirnowku.picturegenerator.picgen;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/picture/{width}/{height}/*")
public class PicGenController {

    private static final Logger log = LoggerFactory.getLogger(PicGenController.class);

    @GetMapping
    public ResponseEntity<?> generate(HttpServletResponse responseToClient,
                                      @PathVariable int width, @PathVariable int height) {
        log.info("generate picture request");
        try {
            HttpGet request = new HttpGet(getUrl(width, height));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);
            response.getEntity().writeTo(responseToClient.getOutputStream());
            httpClient.close();
        } catch (Exception e) {
            log.warn("error", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("generate picture request successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getUrl(int width, int height) {
        return String.format("https://placeimg.com/%d/%d", width, height);
    }
}
