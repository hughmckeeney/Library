package ibtwlb.ibtwlb.controller;

import ibtwlb.ibtwlb.models.*;
import ibtwlb.ibtwlb.repositories.*;
import ibtwlb.ibtwlb.UserService.*;
import ibtwlb.ibtwlb.security.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ArtifactsRepository artifactsRepository;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    // USER LOGIN
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    // USER LOGOUT
    @PostMapping("/logout")
    public String postLogout() {
        return "logout";
    }

    // USER SIGNUP
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(User user) {

        System.out.println("ADDING USER..");
        userService.save(user);
        System.out.println(user.getUsername());
        return "login";
    }

    // USER PROFILES
    @GetMapping("/user")
    public String viewUser(Model model, Principal principal) throws ParseException {
        String username = principal.getName();
        User thisUser = userRepository.findByUsername(username);
        List<Loan> allLoans = loanRepository.findByUserId(thisUser.getId());
        List<Loan> currentLoans = new ArrayList<Loan>();
        for (Loan loan : allLoans) {
            loan.setArtifactName(artifactsRepository.findById(loan.getArtifactId()).getName()); 
            if(!checkIfLoanExpired(loan)){
                currentLoans.add(loan);
            }
        }

        model.addAttribute("loans", allLoans);
        model.addAttribute("currentLoans", currentLoans);
        model.addAttribute("user", thisUser);
        return "user";
    }


    @GetMapping("/user/edit")
    public String viewUser(Principal principal, Model model) {
        String username = principal.getName();
        User thisUser = userRepository.findByUsername(username);
        model.addAttribute("loans", loanRepository.findByUserId(thisUser.getId()));
        model.addAttribute("user", thisUser);
        return "editUser";
    }

    @PostMapping("/user/edit")
    public String editUser(User newUser, Principal principal) {
        String username = principal.getName();
        User thisUser = userRepository.findByUsername(username);
        if(newUser.getBio() != null && newUser.getLocation() != null && newUser.getEmail() != null) {
            thisUser.setBio(newUser.getBio());
            thisUser.setLocation(newUser.getLocation());
            thisUser.setEmail(newUser.getEmail());
            userRepository.save(thisUser);
        }        
        return "redirect:/user";
    }

//Librarian and user profiles. 
    @GetMapping("/userAdmin")
    public String viewUserAdmin(@RequestParam(name="id") String id, Model model) throws ParseException {
        String[] allId = id.split(","); 
        int _id = Integer.parseInt(allId[0]);
        User thisUser = userRepository.findById(_id);
        List<Loan> allLoans = loanRepository.findByUserId(thisUser.getId());
        List<Loan> currentLoans = new ArrayList<Loan>();
        for (Loan loan : allLoans) {
            loan.setArtifactName(artifactsRepository.findById(loan.getArtifactId()).getName());
            if(!checkIfLoanExpired(loan)){
                currentLoans.add(loan);
            }
        }

        model.addAttribute("loans", allLoans);
        model.addAttribute("currentLoans", currentLoans);
        model.addAttribute("user", thisUser);
        return "user";
    }

    //ADMIN ACCESS (LIBRARIAN)
    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    public Boolean checkIfLoanExpired(Loan loan) throws ParseException {
        Boolean expired = false;
        Artifact artifact = artifactsRepository.findById(loan.getArtifactId());
        
        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date expiryDate = dateFormat.parse(loan.getLoanEndDate());
        if (!(expiryDate.compareTo(today) >= 0)) {
            artifact.setOnLoan(false);
            artifactsRepository.save(artifact);
            artifactsRepository.save(artifact);
            expired=true;
        }
        return expired;
    }

}
