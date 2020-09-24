package ibtwlb.ibtwlb.controller;

import ibtwlb.ibtwlb.models.*;
import ibtwlb.ibtwlb.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;




@Controller
public class ArtifactController {
    @Autowired 
    ArtifactsRepository artifactRepository;
    
    @GetMapping("/addartifact")
    public String addartifact() {
        return "addartifact.html";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/addartifact")
    public String saveArtifact(Artifact newArtifact)
    {
        artifactRepository.save(newArtifact);
        return "index.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/deleteartifact")
    public String deleteartifact(@RequestParam(name="id") String id)
    {
        System.out.println("Here");
        String[] allId = id.split(","); 
        int _id = Integer.parseInt(allId[0]);
        artifactRepository.deleteById(_id);
        return "redirect:";
    }

     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/deleteartifact")
    public String deleteArtifactTable(@RequestParam(name="id") String id)
    {
        System.out.println("Here");
        String[] allId = id.split(","); 
        int _id = Integer.parseInt(allId[0]);
        artifactRepository.deleteById(_id);
        return "redirect:";
    }
}