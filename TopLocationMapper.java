import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TopLocationMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text locationDesc = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (line.startsWith("ID,Date")) return; // Skip header

        String[] columns = line.split(",");
        // Column index 4 is the 'Location Description'
        if (columns.length > 4) {
            String location = columns[4].trim();
            // Basic data safety checks
            if (!location.isEmpty() && !location.contains(".") && !location.matches("\\d+")) {
                locationDesc.set(location);
                context.write(locationDesc, one);
            }
        }
    }
}
