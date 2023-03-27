package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Time {
    private LocalDateTime start;
    @JsonIgnore // Ignore printing to JSON file
    private LocalDateTime end;
    private Duration duration;
}
