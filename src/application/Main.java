package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.nextLine();


        try(BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Sale> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
                line = br.readLine();
            }

            System.out.println();
            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio: ");
            Comparator<Sale> comp = (p1, p2) -> p1.averagePrice().compareTo(p2.averagePrice());

            List<Sale> avg = list.stream()
                    .filter(s -> s.getYear() == 2016)
                    .sorted(comp.reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            avg.forEach(System.out::println);

            Double sum = list.stream()
                    .filter(x -> x.getSeller().equals("Logan"))
                    .filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
                    .map(s -> s.getTotal())
                    .reduce(0.0, (x,y) -> x + y);

            System.out.println();
            System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", sum));



        }
        catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        sc.close();
    }
}