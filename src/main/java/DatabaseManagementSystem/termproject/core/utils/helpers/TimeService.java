package DatabaseManagementSystem.termproject.core.utils.helpers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class TimeService {

    private static ZoneId zone;

    @Value("${application.utils.time-service.zone}")
    public void setZone(String zoneId) {
        zone = ZoneId.of(zoneId);
    }

    public static ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now(zone);
    }

}
