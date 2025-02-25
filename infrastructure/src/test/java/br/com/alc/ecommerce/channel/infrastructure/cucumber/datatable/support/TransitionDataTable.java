package br.com.alc.ecommerce.channel.infrastructure.cucumber.datatable.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.test.web.servlet.ResultActions;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransitionDataTable implements Serializable {

    private ResultActions response;

}