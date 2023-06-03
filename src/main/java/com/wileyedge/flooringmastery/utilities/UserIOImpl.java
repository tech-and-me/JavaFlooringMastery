package com.wileyedge.flooringmastery.utilities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.wileyedge.flooringmastery.dto.ProductType;
import com.wileyedge.flooringmastery.dto.StateAbbrev;


@Component
public class UserIOImpl implements IUserIO {
	private static Scanner scanner = new Scanner(System.in);

	@Override
	public void print(String message) {
		System.out.println(message);		
	}

	@Override
	public int getInputAsInteger(String varName, String prompt) {
		int input = 0;
		boolean validInput = false;

		do {
			try {
				System.out.println(prompt);
				input = scanner.nextInt();
				scanner.nextLine();
				validInput = true;
			} catch (InputMismatchException e) {
				System.out.println(varName +  " must be an integer.");
				scanner.nextLine(); // consume invalid input
			}catch (Exception e) {
				System.out.println("Oops! Something went wrong. ");
				return -1;
			}

		} while (!validInput);

		return input;
	}


	@Override
	public int getInputAsInteger(String varName, String prompt, int min, int max) {
		int input = 0;
		boolean validInput = false;

		do {
			try {
				System.out.println(prompt);
				input = scanner.nextInt();
				scanner.nextLine();

				if (input < min || input > max) {
					System.out.println(varName + " must be between " + min + " and " + max + ". Please try again.");
					continue;
				}

				validInput = true;
			} catch (InputMismatchException e) {
				System.out.println(varName + " must be an integer.");
				scanner.nextLine(); // consume invalid input
			} catch (Exception e) {
				System.out.println("Oops! Something went wrong. Please try again.");
				return -1;
			}
		} while (!validInput);

		return input;
	}


	@Override
	public double getInputAsDouble(String varName, String prompt) {
		boolean validInput = false;
		double input = 0;

		do {
			try {
				System.out.println(prompt);
				input = scanner.nextDouble();
				scanner.nextLine();
				validInput = true;
			} catch (InputMismatchException e) {
				System.out.println(varName + " must be a valid double ");
				scanner.nextLine();
			} catch (Exception e) {
				System.out.println("Oooop! Something went wrong !");
			}
		} while (!validInput);

		return input;
	}

	@Override
	public long getInputAsLong(String varName, String prompt) {
		boolean validInput = false;
		long input = 0;

		do {
			try {
				System.out.println(prompt);
				input = scanner.nextLong();
				scanner.nextLine();
				validInput = true;
			} catch (InputMismatchException e) {
				System.out.println(varName + " must be a valid integer");
				scanner.nextLine();
			} 
		} while (!validInput);

		return input;
	}

	@Override
	public String getInputAsString(String varName, String prompt) {
		boolean validInput = false;
		String input = "";

		do {
			try {
				System.out.println(prompt);
				input = scanner.nextLine();
				validInput = true;
			} 
			catch (Exception e) {
				System.out.println("Ooops! Something went wrong. ");
				System.out.println(e.getMessage());
			}
		} while (!validInput);

		return input;
	}

	@Override
	public BigDecimal getInputAsBigDecimal(String varName, String prompt) {
		boolean validInput = false;
		BigDecimal input = BigDecimal.ZERO;

		do {
			try {
				System.out.println(prompt);
				String inputString = scanner.nextLine();
				input = new BigDecimal(inputString);
				validInput = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid decimal number.");
			} catch (Exception e) {
				System.out.println("Oops! Something went wrong.");
				System.out.println(e.getMessage());
			}
		} while (!validInput);

		return input;
	}

	@Override
	public BigDecimal getInputAsPositiveBigDecimal(String varName, String prompt) {
		boolean validInput = false;
		BigDecimal input = BigDecimal.ZERO;

		do {
			try {
				System.out.println(prompt);
				String inputString = scanner.nextLine();
				input = new BigDecimal(inputString);

				// Check if the input is positive
				if (input.compareTo(BigDecimal.ZERO) <= 0) {
					throw new NumberFormatException(varName + " must be a positive decimal number.");
				}

				validInput = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid positive decimal number.");
			} catch (Exception e) {
				System.out.println("Oops! Something went wrong.");
				System.out.println(e.getMessage());
			}
		} while (!validInput);

		return input;
	}

	@Override
	public LocalDate getInputAsDate(String varName, String prompt) {
		boolean validInput = false;
		LocalDate date = null;

		do {
			try {
				System.out.println(prompt);
				String input = scanner.nextLine();

				// Parse the input string into a LocalDate object
				date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				validInput = true;
			} catch (DateTimeParseException e) {
				System.out.println(varName + " must be a valid date in the format dd/MM/yyyy. try again");
			} catch (Exception e) {
				System.out.println("Oops! Something went wrong. try again ");
			}
		} while (!validInput);

		return date;
	}	

	@Override
	public LocalDate getInputAsFutureDate(String varName, String prompt) {
		boolean validInput = false;
		LocalDate date = null;

		do {
			try {
				System.out.println(prompt);
				String input = scanner.nextLine();

				// Parse the input string into a LocalDate object
				date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

				// Check if the date is in the future
				LocalDate currentDate = LocalDate.now();
				if (date.isBefore(currentDate)) {
					System.out.println(varName + " must be a future date. Please try again.");
					continue;
				}

				validInput = true;
			} catch (DateTimeParseException e) {
				System.out.println(varName + " must be a valid date in the format dd/MM/yyyy and in the future. Please try again.");
			} catch (Exception e) {
				System.out.println("Oops! Something went wrong. Please try again.");
			}
		} while (!validInput);

		return date;
	}

	@Override
	public ProductType getInputAsProductType(String varName, String prompt) {
		boolean validInput = false;
		ProductType productType = null;

		do {
			try {
				System.out.println(prompt);
				String input = scanner.nextLine().toUpperCase();
				productType = ProductType.valueOf(input);
				validInput = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid " + varName + "! Please choose from the following product types:");

				// Display the list of correct product types
				for (ProductType type : ProductType.values()) {
					System.out.println(type.name());
				}
			} catch (Exception e) {
				System.out.println("Oops! Something went wrong.");
				System.out.println(e.getMessage());
			}
		} while (!validInput);

		return productType;
	}


	@Override
	public String getInputAsStringYN(String prompt) {
		String input = "";

		do {
			System.out.println(prompt);
			input = scanner.nextLine().trim().toUpperCase();

			if (input.equals("Y") || input.equals("N")) {
				break;
			} else {
				System.out.println("Invalid input! Please enter 'Y' or 'N'.");
			}
		} while (true);

		return input;
	}

	@Override
	public StateAbbrev getInputAsState(String varName, String prompt) {
		boolean validInput = false;
		StateAbbrev stateAbbrev = null;

		do {
			try {
				System.out.println(prompt);
				String input = scanner.nextLine().toUpperCase();
				stateAbbrev = StateAbbrev.valueOf(input);
				validInput = true;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid " + varName + "! Please choose from the following states:");

				// Display the list of correct states
				for (StateAbbrev state : StateAbbrev.values()) {
					System.out.println(state.name());
				}
			} catch (Exception e) {
				System.out.println("Oops! Something went wrong.");
				System.out.println(e.getMessage());
			}
		} while (!validInput);

		return stateAbbrev;
	}


}
