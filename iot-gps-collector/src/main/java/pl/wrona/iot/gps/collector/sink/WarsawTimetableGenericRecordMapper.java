package pl.wrona.iot.gps.collector.sink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import pl.wrona.iot.gps.collector.timetable.Timetable;

@AllArgsConstructor
public class WarsawTimetableGenericRecordMapper implements GenericRecordMapper<Timetable> {
    @Getter
    private final Schema schema;

    @Override
    public GenericData.Record apply(Timetable timetable) {
        GenericData.Record record = new GenericData.Record(schema);

        record.put("stop_id", timetable.getStopId());
        record.put("stop_number", timetable.getStopNumber());

        record.put("line", timetable.getLine());
        record.put("brigade", timetable.getBrigade());
        record.put("direction", timetable.getDirection());

        record.put("time", timetable.getTime());

        return record;
    }
}
