package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.watch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WatchDataTable implements Serializable {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

}