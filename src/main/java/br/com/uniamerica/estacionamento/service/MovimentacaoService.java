package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;


    public Movimentacao cadastrar(Movimentacao movimentacao){

        Assert.notNull(movimentacao.getCondutor(), "O campo 'condutor' não pode ser nulo!");
        Assert.notNull(movimentacao.getEntrada(), "O campo 'entrada' não pode ser nulo!");
        Assert.notNull(movimentacao.getVeiculo(), "O campo 'veiculo' não pode ser nulo!");

        final Condutor condutor = this.condutorRepository.findById(movimentacao.getCondutor().getId()).orElse(null);
        Assert.notNull(condutor, "Condutor não localizado!");
        Assert.isTrue(condutor.isAtivo(),"Condutor não está ativo!");
        final Veiculo veiculo = this.veiculoRepository.findById(movimentacao.getVeiculo().getId()).orElse(null);
        Assert.notNull(veiculo, "Veiculo não localizado!");
        Assert.isTrue(veiculo.isAtivo(),"Veículo não está ativo!");

        return this.movimentacaoRepository.save(movimentacao);
    }
    public Movimentacao atualizarMovimentacao(Movimentacao movimentacao){

        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(movimentacao.getId()).orElse(null);
        Assert.notNull(movimentacaoBanco, "Movimentação não localizada!");
        Assert.isTrue(movimentacaoBanco.getId().equals(movimentacao.getId()), "Movimentação informada indefere com a cadastrada!");

        Assert.notNull(movimentacao.getCondutor(), "O campo 'condutor' não pode ser nulo!");
        Assert.notNull(movimentacao.getEntrada(), "O campo 'entrada' não pode ser nulo!");
        Assert.notNull(movimentacao.getVeiculo(), "O campo 'veiculo' não pode ser nulo!");


        final Condutor condutor = this.condutorRepository.findById(movimentacao.getCondutor().getId()).orElse(null);
        Assert.notNull(condutor, "Condutor não localizado!");
        final Veiculo veiculo = this.veiculoRepository.findById(movimentacao.getVeiculo().getId()).orElse(null);
        Assert.notNull(veiculo, "Veiculo não localizado!");

/*
        Duration duration = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());
        Long duracao;
        duracao = duration.toMinutes();

*/

        final Configuracao configuracao = this.configuracaoRepository.findByConfiguracao();
        LocalDateTime entrada = movimentacao.getEntrada();
        LocalDateTime saida = movimentacao.getSaida();

        Duration tempoEstacionado = Duration.between(entrada, saida);

        long tempoEstacionadoTotal = tempoEstacionado.toMinutes();
        movimentacao.setTempoEstacionadoMinutos(tempoEstacionadoTotal);



        int ano = saida.getYear() - entrada.getYear(); //Subtraindo o ano de entrada e o ano de saida
        int dias = saida.getDayOfYear() - entrada.getDayOfYear(); //Subtraindo o dia de entrada e o dia de saida

        long tempoTotal = Duration.between(entrada, saida).toMinutes(); //Convertendo a duração entre as datas em minutos.

        int TempoTotalMinutos = 0;
        /***
         * Caso a entrada seja antes do inicio do expediente, ou caso a saida seja após o final do expediente.

         */
        if (LocalTime.from(entrada).isBefore(configuracao.getInicioExpediente()) || LocalTime.from(saida).isAfter(configuracao.getFimExpediente())) {

            if(LocalTime.from(entrada).isBefore(configuracao.getInicioExpediente())) {

                TempoTotalMinutos += Duration.between(LocalTime.from(entrada), configuracao.getInicioExpediente()).toMinutes();
            }


            if(LocalTime.from(saida).isAfter(configuracao.getFimExpediente())) {

                TempoTotalMinutos += Duration.between(LocalTime.from(saida), configuracao.getFimExpediente()).toMinutes();

            }
        }
        /***
         * Calculando os dias
         */
        if ( ano > 0 ) { //Caso tenha mais que um ano, ele irá acrescentar 365 dias.
            dias += 365*ano;
        }
        if (dias > 0){
            int foraExpediente = (int) Duration.between(configuracao.getInicioExpediente(), configuracao.getFimExpediente()).toMinutes();
            int duracaoTotal = (int) tempoTotal;


            TempoTotalMinutos += duracaoTotal - (dias * foraExpediente);



            if (LocalTime.from(saida).isAfter(configuracao.getInicioExpediente()) && LocalTime.from(saida).isBefore(configuracao.getFimExpediente()) ) {
                int TempoSaida = (int) Duration.between(LocalTime.from(saida), configuracao.getInicioExpediente()).toMinutes();
                TempoSaida *= -1;
                TempoTotalMinutos -= TempoSaida;

            }


        }


        movimentacao.setValorHoraMulta(BigDecimal.valueOf(TempoTotalMinutos/60));
        movimentacao.setTempoMultaMinutos(TempoTotalMinutos%60);


        final BigDecimal valorMulta = new BigDecimal(TempoTotalMinutos/60).multiply(configuracao.getValorMinutoMulta());


        movimentacao.setValorMulta(valorMulta);
/*
        Duration duration = Duration.between(entrada, saida);
        long duracao;
        duracao = duration.toMinutes();
*/
        // Setando na movimentação as Horas, Minutos e valorHoraMinuto
        movimentacao.setMinutostempo((int)tempoTotal%60);
        movimentacao.setHorastempo((int)tempoTotal/60);

        // Calcula o valor total

        movimentacao.setValorTotal(                 //Calculando o valor total multiplicando as horas da movimentação com o valor hora e somando os minutos multiplicando pelo valor hora e dividindo por 60
                new BigDecimal(movimentacao.getHorastempo()).multiply(movimentacao.getValorHora())
                        .add(new BigDecimal(movimentacao.getMinutostempo()).multiply(movimentacao.getValorHora()
                                .divide(new BigDecimal(60), RoundingMode.HALF_UP))) //Arredondando para 60
                        .add(valorMulta));//Somando o valor da multa




        return this.movimentacaoRepository.save(movimentacao);

    }
    @Transactional
    public ResponseEntity<?> desativar(Long id){

        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);
        Assert.notNull(movimentacao, "Movimentação não localizada!");

        movimentacao.setAtivo(false);
        return ResponseEntity.ok("Movimentação deletada.");
    }

}
