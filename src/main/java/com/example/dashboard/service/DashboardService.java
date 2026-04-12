package com.example.dashboard.service;

import com.example.dashboard.dto.DashboardResponse;

public interface DashboardService {
    DashboardResponse getUserDashboard(Long userId);
}
