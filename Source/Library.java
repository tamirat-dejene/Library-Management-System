import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Library {
    private static int total_number_of_workers;
    private static int total_number_of_books;
    private static int total_number_of_users;

    private ArrayList<Worker> workers;
    private ArrayList<User> users;
    private HashMap<Book, Integer> books;
    private HashMap<User, List<Book>> borrowedBooks;

    public Library() {
        total_number_of_books = 0;
        total_number_of_workers = 0;
        total_number_of_users = 0;

        this.books = new HashMap<>();
        this.users = new ArrayList<User>();
        this.workers = new ArrayList<Worker>();
        this.borrowedBooks = new HashMap<User, List<Book>>();
    }

    public void librarian_login() {
        Worker worker = new Worker(null, null, null, null, null);
        workers.add(worker);
    }
    
    public void hire_new_worker(Worker newWorker) {
        boolean worker_already_exist = false;
        for (Worker w : workers) {
            if (w.getIdNumber().equals(newWorker.getIdNumber()))
                worker_already_exist = true;
        }
        if (!worker_already_exist) {
            workers.add(newWorker);
            ++total_number_of_workers;
        }
    }

    public void fire_worker(Worker worker) {
        for (Worker w : workers) {
            if (w.getIdNumber().equals(worker.getIdNumber())) {
                workers.remove(w);
                --total_number_of_workers;
                return;
            }
        }
    }

    public void register_new_user(User new_user) {
        boolean userAlreadyExist = false;
        for (User u : users) {
            if (u.getIdNumber().equals(new_user.getIdNumber()))
                userAlreadyExist = true;
        }
        if (!userAlreadyExist) {
            users.add(new_user);
            ++total_number_of_users;
        }
    }

    public void verify_user(User u) {
        User found_user = null;
        for (User user : users) {
            if (user.equals(u)) {
                found_user = user;
                break;
            }
        }
        if (found_user != null)
            System.out.println("User " + found_user.getFullName() + " has been verified.");
        else
            System.out.println("No such user exists in the library system.");
    }

    public void addBook(Book new_book, int quantity) {
        if (books.containsKey(new_book)) {
            int existingQuantity = books.get(new_book);
            books.put(new_book, existingQuantity + quantity); // If already exist: overwrite with new quantity
        } else {
            books.put(new_book, quantity);
        }
        total_number_of_books += quantity;
    }

    public boolean isVerifiedUser(User u) {
        for (User user : users)
            if (user.equals(u))
                return true;
        return false;
    }
    
    public void borrowBook(User u, Book b) {
        if (isVerifiedUser(u)) {
            if (books.containsKey(b)) {
                Integer availableCopies = books.get(b);
                if (availableCopies > 0) {
                    --availableCopies;      //                              ////////////////////////////////////////////
                    books.replace(b, availableCopies);
                } else
                    System.out.println("Sorry! The book you want to borrow is not available.");
            } else
                System.out.println("You are not a validated user. Please login first.");
        } else
            System.out.println("The book does not exist in our library system.");
    }
    public void printAllAvailableBookTitleandAuthor() {
        ArrayList<String> bookList = new ArrayList<>();
        for (HashMap.Entry<Book, Integer> entry : books.entrySet())
            bookList.add(entry.getKey().getTitle() + " by " + entry.getKey().getAuthors());

        Collections.sort(bookList);
        System.out.print("\n\t");
        for (int i = 0; i < bookList.size(); ++i) {
            System.out.print(bookList.get(i));
            if (i % 5 == 4)
                System.out.println();
            else
                System.out.print(", ");
        }
        System.out.println();
    }

    
    public Worker viewWorkerInfo(String workerId) {
        Worker w = getWorkerById(workerId);
        if (w != null) {
            return w;
        } else {
            return null;
        }
    }

    public void viewUser(String userId) {
        User u = getUserById(userId);
        if (u != null) {
            System.out.println(u.toString());
        } else {
            System.out.println("The specified user does not exist!");
        }

    }

    public User getUserById(String id) {
        for (User u : users)
            if (u.getIdNumber().equalsIgnoreCase(id))
                return u;
        return null;
    }
    
    public Worker getWorkerById(String id) {
        for (Worker worker : workers) {
            if (worker.getIdNumber().equalsIgnoreCase(id)) {
                return worker;
            }
        }
        return null;
    }
    

    //Returns the number of available copies of a book in our library
    public int getAvailabilityOfABook(Book b) {
        Integer availability = books.get(b);
        if (availability == null)
            throw new IllegalArgumentException("The given book is not in our library");
        return availability;
    }

    public static int getTotalNumberOfBooks() {
        return total_number_of_books;
    }

    public static int getTotalNumberOfWorkers() {
        return total_number_of_workers;
    }

    public static int getTotalNumberOfUsers() {
        return total_number_of_users;
    }



    public static void main(String[] args) {
        
    }
    
}
