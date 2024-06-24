package contract.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

import contract.model.entites.Contract;
import contract.model.entites.Installment;
import contract.model.exceptions.ContractException;
import contract.model.exceptions.InvalidDateException;
import contract.model.exceptions.InvalidNumberException;
import contract.model.exceptions.InvalidValueException;
import contract.model.service.ContractService;
import contract.model.service.PaypalService;

public class Program {

	private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Entre os dados do contrato:");
			int number = readContractNumber(sc);
			LocalDate date = readContractDate(sc);
			double totalValue = readContractValue(sc);

			Contract obj = new Contract(number, date, totalValue);

			int n = readNumberOfInstallments(sc);

			ContractService contractService = new ContractService(new PaypalService());

			contractService.processContract(obj, n);

			System.out.println("Parcelas:");
			for (Installment installment : obj.getInstallments()) {
				System.out.println(installment);
			}
		} catch (ContractException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private static int readContractNumber(Scanner sc) throws InvalidNumberException {
		System.out.print("Número: ");
		if (!sc.hasNextInt()) {
			throw new InvalidNumberException("Número do contrato inválido");
		}
		return sc.nextInt();
	}

	private static LocalDate readContractDate(Scanner sc) throws InvalidDateException {
		System.out.print("Data (dd/MM/yyyy): ");
		String dateStr = sc.next();
		try {
			return LocalDate.parse(dateStr, fmt);
		} catch (DateTimeParseException e) {
			throw new InvalidDateException("Formato de data inválido");
		}
	}

	private static double readContractValue(Scanner sc) throws InvalidValueException {
		System.out.print("Valor do contrato: ");
		if (!sc.hasNextDouble()) {
			throw new InvalidValueException("Valor do contrato inválido");
		}
		return sc.nextDouble();
	}

	private static int readNumberOfInstallments(Scanner sc) throws InvalidNumberException {
		System.out.print("Entre com o número de parcelas: ");
		if (!sc.hasNextInt()) {
			throw new InvalidNumberException("Número de parcelas inválido");
		}
		return sc.nextInt();
	}
}