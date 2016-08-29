package kr.ec.queryfly.analyzer.stat;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bson.types.ObjectId;

import com.google.common.base.Splitter;

import kr.ec.queryfly.analyzer.model.Fly;
import kr.ec.queryfly.analyzer.model.QaPair;


/**
 * version1. only supports Google Forms' result CSV
 * 
 * @author Juho Kang
 *
 */
public class CSVtoFliesConverter {


  public List<Fly> parse(String csv, ObjectId flybaseId) {

    List<Fly> result = new ArrayList<Fly>();

    List<String> rows = splitToRows(csv);

    String notParsedQuestions = rows.get(0).replace("\"", "");
    List<String> questions = Splitter.on(",").splitToList(notParsedQuestions);


    for (int i = 1; i < rows.size(); i++) {
      String notParsedAnswers = rows.get(i).replace("\"", "");
      List<String> answers = Splitter.on(",").splitToList(notParsedAnswers);
      List<QaPair> qaPairs = new ArrayList<QaPair>();

      for (int j = 1; j < answers.size(); j++) {
        QaPair aPair = new QaPair.Builder(questions.get(j)).answer(answers.get(j)).build();
        qaPairs.add(aPair);
      }

      // LocalDateTime.parse(answers.get(0), format).atZone(ZoneId.of("+09"))
      // the google form only has local time. converted to UTC+09
      // this part is too buggy. used a turn around adding a whitespace
      // http://stackoverflow.com/questions/37287103/why-does-gmt8-fail-to-parse-with-pattern-o-despite-being-copied-straight-ou

      DateTimeFormatter format =
          DateTimeFormatter.ofPattern("yyyy/MM/dd h:mm:ss a O ", Locale.ENGLISH);
      Fly fly = new Fly.Builder(qaPairs).flybaseId(flybaseId)
          .createTime(ZonedDateTime.parse(answers.get(0) + " ", format)).build();

      result.add(fly);

    }

    return result;
  }

  private List<String> splitToRows(String csv) {
    String lineSeperator = "\n";
    if (csv.contains("\r\n")) {
      lineSeperator = "\r\n";
    } else if (csv.contains("\r")) {
      lineSeperator = "\r";
    } else if (csv.contains("\n")) {
      lineSeperator = "\n";
    }

    return Splitter.on(lineSeperator).splitToList(csv);
  }

}
