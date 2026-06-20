import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class YearlyDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: YearlyDriver <input> <output>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        // FORCE LOCAL MODE TO PREVENT RAM CRASHES
        conf.set("mapreduce.framework.name", "local");
        conf.set("fs.defaultFS", "file:///"); 

        Job job = Job.getInstance(conf, "Safe Crash-Proof Year Trend");
        job.setJarByClass(YearlyDriver.class);
        job.setMapperClass(YearlyMapper.class);
        job.setReducerClass(YearlyReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
