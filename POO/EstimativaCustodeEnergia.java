

import javax.swing.JOptionPane;

public class EstimativaCustodeEnergia {

    private int consumoInicial;
    private double tarifa;

    public EstimativaCustoEnergia(int consumoInicial, double tarifa) {
        this.consumoInicial = consumoInicial;
        this.tarifa = tarifa;
    }

    public int getConsumoInicial() {
        return consumoInicial;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void calcularEstimativa() {
        StringBuilder resultado = new StringBuilder("Estimativa de Custo de Energia Elétrica (Próximos 12 meses):\n");

        for (int mes = 1; mes <= 12; mes++) {
            int consumoMensal = consumoInicial + (mes * 100);
            double custoFornecimento = consumoMensal * tarifa;

            double fatorMultiplicacaoICMS = (consumoMensal <= 200) ? 0.136363 : 0.333333;
            double icms = fatorMultiplicacaoICMS * custoFornecimento;

            double fatorMultiplicacaoCOFINS = (consumoMensal <= 200) ? 0.0614722 : 0.0730751;
            double cofins = fatorMultiplicacaoCOFINS * custoFornecimento;

            double fatorMultiplicacaoPISPASEP = (consumoMensal <= 200) ? 0.013346 : 0.0158651;
            double pisPasep = fatorMultiplicacaoPISPASEP * custoFornecimento;

            double icmsSobreCofins = fatorMultiplicacaoICMS * fatorMultiplicacaoCOFINS * custoFornecimento;
            double icmsSobrePisPasep = fatorMultiplicacaoICMS * fatorMultiplicacaoPISPASEP * custoFornecimento;

            double fatura = custoFornecimento + icms + cofins + pisPasep + icmsSobreCofins + icmsSobrePisPasep;

            resultado.append(String.format("Mês %d: Consumo = %d kWh, Fornecimento = R$ %.2f, ICMS = R$ %.2f, COFINS = R$ %.2f, PIS/PASEP = R$ %.2f, ICMS/COFINS = R$ %.2f, ICMS/PIS/PASEP = R$ %.2f, Fatura = R$ %.2f%n", 
                                          mes, consumoMensal, custoFornecimento, icms, cofins, pisPasep, icmsSobreCofins, icmsSobrePisPasep, fatura));
        }

        JOptionPane.showMessageDialog(null, resultado.toString(), "Estimativa de Custo de Energia", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        String consumoInicialStr = JOptionPane.showInputDialog("Digite o consumo inicial (kWh):");
        String tarifaStr = JOptionPane.showInputDialog("Digite a tarifa (R$ por kWh):");

        try {
            int consumoInicial = Integer.parseInt(consumoInicialStr);
            double tarifa = Double.parseDouble(tarifaStr);

            EstimativaCustoEnergia estimativa = new EstimativaCustoEnergia(consumoInicial, tarifa);
            estimativa.calcularEstimativa();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Certifique-se de digitar números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
              
