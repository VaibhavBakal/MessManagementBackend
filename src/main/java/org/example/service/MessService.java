package org.example.service;

import jakarta.validation.constraints.Email;
import org.example.entity.DailyMenu;
import org.example.entity.Note;
import org.example.entity.TiffinRequest;
import org.example.entity.User;
import org.example.exception.BadRequestException;
import org.example.exception.ResourceNotFoundException;
import org.example.repositiories.DailyMenuRepository;
import org.example.repositiories.NoteRepository;
import org.example.repositiories.TiffinRequestRepository;
import org.example.repositiories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TiffinRequestRepository tiffinRequestRepository;
    @Autowired
    private DailyMenuRepository dailyMenuRepository;
    @Autowired
    private NoteRepository noteRepository;

    // 1. Register User
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists!");
        }
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }

    // 2. Login
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        if (!user.getPassword().equals(password)) {
            throw new BadRequestException("Invalid credentials!");
        }
        return user;
    }

    // 3. Send Tiffin Request
    public TiffinRequest createTiffinRequest(Long userId, TiffinRequest request, String customAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        request.setUser(user);
        // If customAddress is provided, use it. Otherwise, fallback to user's saved address.
        if (customAddress != null && !customAddress.isBlank()) {
            request.setDeliveryAddress(customAddress);
        } else {
            request.setDeliveryAddress(user.getAddress());
        }
        request.setStatus("PENDING");
        return tiffinRequestRepository.save(request);
    }

    // 4. Admin - Update User
    public User updateUserByAdmin(Long userId, User updatedData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setUsername(updatedData.getUsername());
        user.setContactNumber(updatedData.getContactNumber());
        user.setAddress(updatedData.getAddress());
        return userRepository.save(user);
    }

    // 5. Admin - Delete User (and cascade delete requests if needed)
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    // 6. Admin - Add Daily Menu
    public DailyMenu addDailyMenu(DailyMenu menu) {
        return dailyMenuRepository.save(menu);
    }

    // 7. Admin - Post Note
    public Note postNote(Note note) {
        note.setPostedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public List<TiffinRequest> getAllRequests() {
        return tiffinRequestRepository.findAll();
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public List<DailyMenu> getAllMenus() {
        return dailyMenuRepository.findAll();
    }

}
