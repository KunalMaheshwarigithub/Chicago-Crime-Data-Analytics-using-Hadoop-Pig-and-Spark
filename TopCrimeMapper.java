import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TopCrimeMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text crimeType = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (line.startsWith("ID,Date")) return; // Skip header

        String[] columns = line.split(",");
        // Column 3 is the Primary Type
        if (columns.length > 3) {
            String type = columns[3].trim();
            if (!type.isEmpty() && !type.contains(".")) { // Skip shifted row errors
                crimeType.set(type);
                context.write(crimeType, one);
            }
        }
    }
}
