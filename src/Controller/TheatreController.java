package Controller;

import Domain.*;
import Service.TheatreService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    public Integer login(EMail eMail) {
        return theatreService.login(eMail);
    }

    public boolean createViewerAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createViewerAccount(id, name, age, eMail);
    }

    public boolean manageViewerAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.manageViewerAccount(id, name, age, eMail);
    }

    public boolean deleteViewerAccount(Integer id) {
        return theatreService.deleteViewerAccount(id);
    }

    public boolean createAdminAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.createAdminAccount(id, name, age, eMail);
    }

    public boolean manageAdminAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.manageAdminAccount(id, name, age, eMail);
    }

    public boolean deleteAdminAccount(Integer id) {
        return theatreService.deleteAdminAccount(id);
    }

    public boolean createActorAccount(Integer id, String name, int age, EMail eMail, int salary) {
        return theatreService.createActorAccount(id, name, age, eMail, salary);
    }

    public boolean manageActorAccount(Integer id, String name, int age, EMail eMail) {
        return theatreService.manageActorAccount(id, name, age, eMail);
    }

    public boolean deleteActorAccount(Integer id) {
        return theatreService.deleteActorAccount(id);
    }

    public boolean changeSalary(Integer id, int salary) {
        return theatreService.changeSalary(id, salary);
    }

    public boolean createAuditorium(Integer id, String name, int rows, int cols) {
        return theatreService.createAuditorium(id, name, rows, cols);
    }

    public boolean deleteAuditorium(Integer id) {
        return theatreService.deleteAuditorium(id);
    }

    public boolean createShow(Integer id, String title, Date date, Integer auditoriumID, Map<Actor, String> roles, int price) {
        return theatreService.createShow(id, title, date, auditoriumID, roles, price);
    }

    public boolean deleteShow(Integer id) {
        return theatreService.deleteShow(id);
    }

    public boolean createOrder(Integer id, Integer viewerID, Integer showID, List<Integer> seats) {
        return theatreService.createOrder(id, viewerID, showID, seats);
    }

    public int deleteOrder(Integer id) {
        return theatreService.deleteOrder(id);
    }


    ////////////////*** VIEW ***////////////////

    public Person viewAccount(EMail eMail) {
        return theatreService.viewAccount(eMail);
    }

    public List<Show> viewActorShows(Integer id) {
        return theatreService.viewActorShows(id);
    }

    public List<Order> viewViewerOrders(Integer id) {
        return theatreService.viewViewerOrders(id);
    }

    public List<Actor> viewActors() {
        return theatreService.getActors();
    }

    public Actor viewActor(Integer id) {
        return theatreService.getActor(id);
    }

    public List<Viewer> viewViewers() {
        return theatreService.getViewers();
    }

    public Viewer viewViewer(Integer id) {
        return theatreService.getViewer(id);
    }

    public List<Admin> viewAdmins() {
        return theatreService.getAdmins();
    }

    public Admin viewAdmin(Integer id) {
        return theatreService.getAdmin(id);
    }

    public List<Auditorium> viewAuditoriums() {
        return theatreService.getAuditoriums();
    }

    public Auditorium viewAuditorium(Integer id) {
        return theatreService.getAuditorium(id);
    }

    public List<Show> viewShows() {
        return theatreService.getShows();
    }

    public List<Show> viewShowsSorted() {
        return theatreService.getShowsSorted();
    }

    public List<Show> viewShowsFiltered() {
        return theatreService.getShowsFiltered();
    }

    public Show viewShow(Integer id) {
        return theatreService.getShow(id);
    }

    public List<Order> viewOrders() {
        return theatreService.getOrders();
    }

    public List<Order> viewOrdersSorted() {
        return theatreService.getOrdersSorted();
    }

    public List<Order> viewOrdersFiltered() {
        return theatreService.getOrdersFiltered();
    }

    public Order viewOrder(Integer id) {
        return theatreService.getOrder(id);
    }

}