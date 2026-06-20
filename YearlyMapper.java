import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class YearlyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text yearText = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (line.startsWith("ID,Date")) return;

        String[] columns = line.split(",");
        if (columns.length > 9) {
            String year = columns[9].trim();
            
            // FIX: Only accept it if it is exactly a 4-digit number (like 2022)
            if (year.matches("\\d{4}")) {
                yearText.set(year);
                context.write(yearText, one);
            }
        }
    }
}
