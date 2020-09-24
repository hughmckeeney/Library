package ibtwlb.ibtwlb.controller;
import ibtwlb.ibtwlb.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import ibtwlb.ibtwlb.models.*;
import ibtwlb.ibtwlb.repositories.*;


@Controller
public class LoanController {
    @Autowired LoanRepository loanRepository;
    
}