import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ArrestMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text arrestKey = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (line.startsWith("ID,Date")) return; // Skip header

        String[] columns = line.split(",");
        // Column index 5 is the 'Arrest' column (true/false)
        if (columns.length > 5) {
            String arrestStatus = columns[5].trim().toLowerCase();
            
            if (arrestStatus.equals("true") || arrestStatus.equals("false")) {
                arrestKey.set(arrestStatus);
                context.write(arrestKey, one);
            }
        }
    }
}
