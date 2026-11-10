package org.example.Controller;

import org.example.dto.ApiResponse;
import org.example.entity.DailyMenu;
import org.example.entity.Note;
import org.example.entity.TiffinRequest;
import org.example.entity.User;
import org.example.service.MessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class MessController {
    @Autowired
    private MessService messService;

    // User Registration
    @PostMapping("/users/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody User user) {
        User savedUser = messService.registerUser(user);
        return ResponseEntity.ok(ApiResponse.created("User registered successfully", savedUser));
    }

    // User Login
    @PostMapping("/users/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestParam String username, @RequestParam String password) {
        User user = messService.loginUser(username, password);
        return ResponseEntity.ok(ApiResponse.success("Login successful", user));
    }

    // Send Tiffin Request (with option for new or default address)
    @PostMapping("/tiffin/request")
    public ResponseEntity<ApiResponse<TiffinRequest>> sendTiffinRequest(
            @RequestParam Long userId,
            @RequestParam(required = false) String newAddress,
            @RequestBody TiffinRequest request) {
        TiffinRequest savedRequest = messService.createTiffinRequest(userId, request, newAddress);
        return ResponseEntity.ok(ApiResponse.created("Tiffin request raised successfully", savedRequest));
    }

    // Get Notes (Visible to all users)
    @GetMapping("/notes")
    public ResponseEntity<ApiResponse<List<Note>>> getNotes() {
        return ResponseEntity.ok(ApiResponse.success("Fetched notes", messService.getAllNotes()));
    }

    // --- Admin Endpoints ---

    // Get all tiffin requests
    @GetMapping("/admin/requests")
    public ResponseEntity<ApiResponse<List<TiffinRequest>>> getAllRequests() {
        return ResponseEntity.ok(ApiResponse.success("Fetched all tiffin requests", messService.getAllRequests()));
    }

    // Update user info
    @PutMapping("/admin/users/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = messService.updateUserByAdmin(id, user);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", updated));
    }

    // Delete user and account data
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        messService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    // Add daily menu
    @PostMapping("/admin/menu")
    public ResponseEntity<ApiResponse<DailyMenu>> addMenu(@RequestBody DailyMenu menu) {
        DailyMenu createdMenu = messService.addDailyMenu(menu);
        return ResponseEntity.ok(ApiResponse.created("Daily menu added successfully", createdMenu));
    }

    @GetMapping("/menu")
    public ResponseEntity<ApiResponse<List<DailyMenu>>> getMenus() {
        return ResponseEntity.ok(ApiResponse.success("Fetched daily menus", messService.getAllMenus()));
    }

    // Add note section
    @PostMapping("/admin/notes")
    public ResponseEntity<ApiResponse<Note>> postNote(@RequestBody Note note) {
        Note createdNote = messService.postNote(note);
        return ResponseEntity.ok(ApiResponse.created("Note posted successfully", createdNote));
    }

}
