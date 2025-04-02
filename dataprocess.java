import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

interface DataProcessor {
    Dataset<Row> process(String data);
}

class SparkProcessor implements DataProcessor {
    private SparkSession spark;
    public SparkProcessor() {
        spark = SparkSession.builder()
                .appName("SustainabilityProcessor")
                .master("local[*]")
                .getOrCreate();
    }
    
    @Override
    public Dataset<Row> process(String data) {
        Dataset<Row> df = spark.read().json(spark.createDataset(
            Collections.signletonList(data), Encoders.STRING()));
            return df.groupBy("timestamp").avg("emissions");
    }
}