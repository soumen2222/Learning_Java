package com.soumen.lambda;
import java.util.*;
import java.util.function.Function;

/**
 *
 * @author José Paumard
 */
public class MainComparator {

    public static void main(String... args) {
        
        Comparator<Person> cmpAge = (p1, p2) -> p2.getAge() - p1.getAge() ;
        Comparator<Person> cmpFirstName = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()) ;
        Comparator<Person> cmpLastName = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName()) ;
        
        Function<Person, Integer> f1 = p -> p.getAge();
        Function<Person, String> f2 = p -> p.getLastName();
        Function<Person, String> f3 = p -> p.getFirstName();

        Comparator<Person> cmpPersonAge = Comparator.comparing(Person::getAge);
        Comparator<Person> cmpPersonLastName = Comparator.comparing(Person::getLastName);


        Comparator<Person> cmp = Comparator.comparing(Person::getLastName)
                                           .thenComparing(Person::getFirstName)
                                           .thenComparing(Person::getAge);

        Person p1 = new Person("soumen", "Ghosh", 49);
        Person p2 = new Person("sanjay", "Ghosh", 45);
        Person p3 = new Person("shib", "Ghosh", 55);
        Person p4 = new Person("som", "Ghosh", 57);

        List<Person> pList = new ArrayList<>();
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
        pList.add(p4);
        Collections.sort(pList,cmp);
        System.out.println("\nSorted by age");
        pList.forEach(System.out::println);


        Predicate<String> pr1 = s -> s.length() < 20 ;
        Predicate<String> pr2 = s -> s.length() > 5 ;

        boolean b = pr1.test("Hello");
        System.out.println("Hello is shorter than 20 chars : " + b);

        Predicate<String> pr3 = pr1.and(pr2) ;

        System.out.println("P3 for Yes: " + pr3.test("Yes"));
        System.out.println("P3 for Good morning: " + pr3.test("Good morning"));
        System.out.println("P3 for Good morning gentlemen: " + pr3.test("Good morning gentlemen"));

        Predicate<String> pr4 = pr1.or(pr2) ;

        System.out.println("P4 for Yes: " + pr4.test("Yes"));
        System.out.println("P4 for Good morning: " + pr4.test("Good morning"));
        System.out.println("P4 for Good morning gentlemen: " + pr4.test("Good morning gentlemen"));

        Predicate<String> pr5 = Predicate.isEqualsTo("Yes");

        System.out.println("P5 for Yes: " + pr5.test("Yes"));
        System.out.println("P5 for No: " + pr5.test("No"));

        Predicate<Integer> pr6 = Predicate.isEqualsTo(1);

        System.out.println("Pr5 for 1: " + pr6.test(2));


        Person person1 = new Person("Alice", 23);
        Person person2 = new Person("Brian", 56);
        Person person3 = new Person("Chelsea", 46);
        Person person4 = new Person("David", 28);
        Person person5 = new Person("Erica", 37);
        Person person6 = new Person("Francisco", 18);

        List<Person> peopleList = new ArrayList<>(Arrays.asList(person1,person2,person3,person4,person5,person6));
        peopleList.sort(Comparator.comparing(Person::getAge).reversed());
        peopleList.replaceAll(person -> new Person(person.getFirstName().toUpperCase(),person.getAge()));
        System.out.println("Printing People after sorting");
        peopleList.forEach(System.out::println);

        City newYork = new City("New York");
        City shanghai = new City("Shanghai");
        City paris = new City("Paris");

        Map<City, List<Person>> map = new HashMap<>();

        map.putIfAbsent(paris, new ArrayList<>());
        map.get(paris).add(person1);

        map.computeIfAbsent(newYork, city -> new ArrayList<>()).add(person2);
        map.computeIfAbsent(newYork, city -> new ArrayList<>()).add(person3);

        System.out.println("People from Paris : " + map.getOrDefault(paris, Collections.EMPTY_LIST));
        System.out.println("People from New York : " + map.getOrDefault(newYork, Collections.EMPTY_LIST));

        Map<City, List<Person>> map1 = new HashMap<>();
        map1.computeIfAbsent(newYork, city -> new ArrayList<>()).add(person1);
        map1.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(person2);
        map1.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(person3);

        System.out.println("Map 1");
        map1.forEach((city, people) -> System.out.println(city + " : " + people));


        Map<City, List<Person>> map2 = new HashMap<>();
        map2.computeIfAbsent(shanghai, city -> new ArrayList<>()).add(person4);
        map2.computeIfAbsent(paris, city -> new ArrayList<>()).add(person5);
        map2.computeIfAbsent(paris, city -> new ArrayList<>()).add(person6);

        System.out.println("Map 2");
        map2.forEach((city, people) -> System.out.println(city + " : " + people));

        map2.forEach(
                (city, people) -> {
                    map1.merge(
                            city, people,
                            (peopleFromMap1, peopleFromMap2) -> {
                                peopleFromMap1.addAll(peopleFromMap2);
                                return peopleFromMap1;
                            });
                }
        );

        System.out.println("Merged map1 ");
        map1.forEach(
                (city, people) -> System.out.println(city + " : " + people)
        );
    }
}
