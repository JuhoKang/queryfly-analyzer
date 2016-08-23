package kr.ec.queryfly.analyzer.stat;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import kr.ec.queryfly.analyzer.model.Fly;

public class CSVtoFlybaseConverterTest {

  CSVtoFlybaseConverter converter;

  String testCase;

  @Before
  public void setUp() {
    converter = new CSVtoFlybaseConverter();

    try {
      testCase = Files.toString(new File(this.getClass().getResource("/testform.csv").toURI()),
          Charsets.UTF_8);
    } catch (IOException | URISyntaxException e1) { // TODO Auto-generated catch block
      e1.printStackTrace();
    }

  }

  @After
  public void tearDown() {

  }

  @Test
  public void testParse() {

    List<Fly> result = converter.parse(testCase);
    assertEquals("Question Select 1", result.get(0).getQaPairs().get(0).getQuestion());
    assertEquals("Question Select 2", result.get(0).getQaPairs().get(1).getQuestion());
    assertEquals("Question Multi 1", result.get(0).getQaPairs().get(2).getQuestion());
    assertEquals("Question Short 1", result.get(0).getQaPairs().get(3).getQuestion());

    // ...



  }
}
