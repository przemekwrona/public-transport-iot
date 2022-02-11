package pl.wrona.iotapollo

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils

import java.nio.charset.StandardCharsets

class JsonFileUtils {
  static ObjectMapper objectMapper = new ObjectMapper();

  public static <E> E readJson(String path, Class<E> clazz) throws IOException {
    return objectMapper.readValue(IOUtils.resourceToString(String.format("/json%s", path), StandardCharsets.UTF_8), clazz);
  }
}
