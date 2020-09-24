package ibtwlb.ibtwlb.controller;

import ibtwlb.ibtwlb.*;
import ibtwlb.ibtwlb.models.*;
import ibtwlb.ibtwlb.repositories.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LibraryController {

    @Autowired
    ArtifactsRepository artifactsRepository;
    @Autowired 
    LoanRepository loanRepository;
    @Autowired 
    UserRepository userRepository;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");


    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/")
    public String postIndex() {
        return "index.html";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "searchQuery") String searchQuery, Model model) {
       
        int id;
        if (isInteger(searchQuery)) {
            id = Integer.parseInt(searchQuery);
            model.addAttribute("ArtifactById", artifactsRepository.findById(id));
        }
        model.addAttribute("ArtifactByName", artifactsRepository.findByAuthorContainingIgnoreCase(searchQuery));
        model.addAttribute("ArtifactByAuthor", artifactsRepository.findByDescriptionContainingIgnoreCase(searchQuery));
        model.addAttribute("ArtifactByType", artifactsRepository.findByNameContainingIgnoreCase(searchQuery));
        return "search.html";
    }

    @GetMapping("/book")
    public String book( @RequestParam(name="id") String id, Model model) throws ParseException{
        
        if (isInteger(id)) {
            int _id = Integer.parseInt(id);
            Artifact artifact= artifactsRepository.findById(_id);
    
            List<Loan> loans=loanRepository.findByArtifactId(_id);
            List<User> users = new ArrayList<User>();
            long userId;
            for(Loan loan : loans){
                userId = loan.getUserId();
                users.add(userRepository.findById(userId));
            }
           
            Collections.reverse(users);
            Collections.reverse(loans);


            model.addAttribute("Artifact", artifact);
            model.addAttribute("Users", users);
            model.addAttribute("Loans", loans);
            model.addAttribute("User", userRepository.findAll());
            
        }else{
            System.out.println("NOT INTEGER");
        }
        return "book.html";
    }
    @PostMapping("/book")
    public String reserveBook(@RequestParam(name="id") String id, @ModelAttribute("Artifact") Artifact Artifact, Model model, RedirectAttributes rA) throws ParseException{
     
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        Artifact = artifactsRepository.findById(Artifact.getId());

        Date startDate = new Date();
        Date availableFrom = new Date();

        List<User> users = new ArrayList<User>();
        List<Loan> loans=loanRepository.findByArtifactId(Artifact.getId());
        long userId;

        for(Loan loan : loans){
            availableFrom = dateFormat.parse(loan.getLoanEndDate());
            if(availableFrom.compareTo(startDate)>0){
                startDate=availableFrom;
            }
            userId = loan.getUserId();
            users.add(userRepository.findById(userId));
        }

        Date endDate= startDate;
        Calendar c = Calendar.getInstance(); 
        c.setTime(endDate); 
        c.add(Calendar.DATE, 7);
        endDate = c.getTime(); 
        
        if (!loans.isEmpty()){
            Loan l = loans.get(loans.size()-1);
            if (l.getUserId()==currentUser.getId()){
                l.setLoanEndDate(dateFormat.format(endDate));
                loanRepository.save(l);
                loans.set(loans.size()-1, l);
            }else{
                Loan newLoan = new Loan(currentUser.getId(),Artifact.getId(), dateFormat.format(startDate), dateFormat.format(endDate), artifactsRepository);
                loanRepository.save(newLoan);
                loans.add(newLoan);
                userId = newLoan.getUserId();
                users.add(userRepository.findById(userId));
            }
        }else{
            Loan newLoan = new Loan(currentUser.getId(),Artifact.getId(), dateFormat.format(startDate), dateFormat.format(endDate), artifactsRepository);
            loanRepository.save(newLoan);
            loans.add(newLoan);
            userId = newLoan.getUserId();
            users.add(userRepository.findById(userId));
        }

        
        Collections.reverse(users);
        Collections.reverse(loans);

        Artifact = artifactsRepository.findById(Artifact.getId());
        setArtifactOnLoan(Artifact, true);
        model.addAttribute("Users", users);
        model.addAttribute("Loans", loans);
        model.addAttribute("Artifact", Artifact);
       
        rA.addAttribute("id", Artifact.getId() );
        return "redirect:/book";
    }

    @PostMapping("/setBookAvailability")
    public String setBookAvailability(@RequestParam(name="id") String id, Model model,RedirectAttributes rA){
        String[] ID = id.split(",");
        
        int _id = Integer.parseInt(ID[0]);
        Artifact artifact = artifactsRepository.findById(_id);
        artifact.setOnLoan(!artifact.getOnLoan());
        artifactsRepository.save(artifact);

        
        rA.addAttribute("id", artifact.getId() );
        return "redirect:/book";
    }

    @PostMapping("/bookforuser")
    public String reserveBookForUser(@RequestParam(name="id") String id, @RequestParam(name="userid") String userid, @ModelAttribute("Artifact") Artifact Artifact, Model model, RedirectAttributes rA) throws ParseException{
     
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        Artifact = artifactsRepository.findById(Artifact.getId());

        Date startDate = new Date();
        Date availableFrom = new Date();

        List<User> users = new ArrayList<User>();
        List<Loan> loans=loanRepository.findByArtifactId(Artifact.getId());
        long userId;

        for(Loan loan : loans){
            availableFrom = dateFormat.parse(loan.getLoanEndDate());
            if(availableFrom.compareTo(startDate)>0){
                startDate=availableFrom;
            }
            userId = loan.getUserId();
            users.add(userRepository.findById(userId));
        }

        Date endDate= startDate;
        Calendar c = Calendar.getInstance(); 
        c.setTime(endDate); 
        c.add(Calendar.DATE, 7);
        endDate = c.getTime();

        String[] allId = userid.split(","); 
        long customerId = Long.parseLong(allId[0]);
        if (!loans.isEmpty()){
            Loan l = loans.get(loans.size()-1);
            if (l.getUserId()==customerId){
                l.setLoanEndDate(dateFormat.format(endDate));
                loanRepository.save(l);
                loans.set(loans.size()-1, l);
            }else{
                Loan newLoan = new Loan(customerId,Artifact.getId(), dateFormat.format(startDate), dateFormat.format(endDate), artifactsRepository);
                loanRepository.save(newLoan);
                loans.add(newLoan);
                users.add(userRepository.findById(customerId));
            }
        }else{
            Loan newLoan = new Loan(customerId,Artifact.getId(), dateFormat.format(startDate), dateFormat.format(endDate), artifactsRepository);
            loanRepository.save(newLoan);
            loans.add(newLoan);
            users.add(userRepository.findById(customerId));
        }

        Collections.reverse(users);
        Collections.reverse(loans);

        Artifact = artifactsRepository.findById(Artifact.getId());
        setArtifactOnLoan(Artifact, true);
        model.addAttribute("Users", users);
        model.addAttribute("Loans", loans);
        model.addAttribute("Artifact", Artifact);

        rA.addAttribute("id", Artifact.getId() );
        return "redirect:/book";
    }

    @GetMapping("/browse")
    public String browse(Model model) {
        model.addAttribute("ARTIFACTS", artifactsRepository.findAll());
        return "browse.html";
    }

    @GetMapping("/delete")
    public String deleteArtifact(Model model) {
        model.addAttribute("ARTIFACTS", artifactsRepository.findAll());
        return "deleteartifact.html";
    }

    @GetMapping("/browseUsers")
    public String browseUsers(Model model) {
        model.addAttribute("User", userRepository.findAll());
        return "browseUsers.html";
    }

    @GetMapping("/searchUser")
    public String searchUser(@RequestParam(name = "searchUser") String searchUser, Model model) {
        model.addAttribute("UserByName", userRepository.findByNameContainingIgnoreCase(searchUser));
        model.addAttribute("UserByUsername", userRepository.findByUsernameContainingIgnoreCase(searchUser));
        model.addAttribute("UserByEmail", userRepository.findByEmailContainingIgnoreCase(searchUser));
        model.addAttribute("UserByLocation", userRepository.findByLocationContainingIgnoreCase(searchUser));
        return "searchUsers.html";
    }

    public boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setArtifactOnLoan(Artifact a, Boolean value) {
        a.setOnLoan(value); 
        artifactsRepository.save(a);
    }
}