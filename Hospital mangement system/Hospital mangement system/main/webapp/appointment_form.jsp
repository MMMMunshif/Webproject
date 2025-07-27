<form action="CreateAppointmentServlet" method="post" id="appointmentForm">
    <div style="margin-bottom: 1.5rem;">
        <label for="patient_name" style="display:block; margin-bottom:0.5rem; font-weight:600; color:#2c3e50;">Patient Name</label>
        <input type="text" id="patient_name" name="patient_name"
            value="Alice Thompson" readonly
            style="width:100%; padding:0.75rem 1rem; font-size:1rem; border:1px solid #ccc; border-radius:4px; outline:none;">
    </div>

    <div style="display: flex; gap: 1rem; margin-bottom: 1.5rem;">
        <div style="flex: 1;">
            <label for="doctor_id" style="display:block; margin-bottom:0.5rem; font-weight:600; color:#2c3e50;">Select Doctor</label>
            <select id="doctor_id" name="doctor_id"
                style="width:100%; padding:0.75rem 1rem; font-size:1rem; border:1px solid #ccc; border-radius:4px;">
                <option value="">-- Select a doctor --</option>
                <option value="1">Dr. John Smith (Cardiology)</option>
                <option value="2">Dr. Jane Smith (Dermatology)</option>
                <option value="3">Dr. Michael Chen (Dermatology)</option>
                <option value="4">Dr. Sarah Johnson (General Medicine)</option>
                <option value="5">Dr. Robert Williams (Orthopedics)</option>
            </select>
        </div>
        <div style="flex: 1;">
            <label for="appointment_type" style="display:block; margin-bottom:0.5rem; font-weight:600; color:#2c3e50;">Appointment Type</label>
            <select id="appointment_type" name="appointment_type"
                style="width:100%; padding:0.75rem 1rem; font-size:1rem; border:1px solid #ccc; border-radius:4px;">
                <option value="">-- Select type --</option>
                <option value="regular">Regular Check-up</option>
                <option value="follow-up">Follow-up Visit</option>
                <option value="consultation">New Consultation</option>
                <option value="emergency">Urgent Care</option>
            </select>
        </div>
    </div>

    <div style="display: flex; gap: 1rem; margin-bottom: 1.5rem;">
        <div style="flex: 1;">
            <label for="appointment_date" style="display:block; margin-bottom:0.5rem; font-weight:600; color:#2c3e50;">Appointment Date</label>
            <input type="date" id="appointment_date" name="appointment_date"
                style="width:100%; padding:0.75rem 1rem; font-size:1rem; border:1px solid #ccc; border-radius:4px;">
        </div>
        <div style="flex: 1;">
            <label for="appointment_time" style="display:block; margin-bottom:0.5rem; font-weight:600; color:#2c3e50;">Appointment Time</label>
            <select id="appointment_time" name="appointment_time"
                style="width:100%; padding:0.75rem 1rem; font-size:1rem; border:1px solid #ccc; border-radius:4px;">
                <option value="">-- Select time --</option>
                <option value="09:00">9:00 AM</option>
                <option value="09:30">9:30 AM</option>
                <option value="10:00">10:00 AM</option>
                <option value="10:30">10:30 AM</option>
                <option value="11:00">11:00 AM</option>
                <option value="11:30">11:30 AM</option>
                <option value="13:00">1:00 PM</option>
                <option value="13:30">1:30 PM</option>
                <option value="14:00">2:00 PM</option>
                <option value="14:30">2:30 PM</option>
                <option value="15:00">3:00 PM</option>
                <option value="15:30">3:30 PM</option>
                <option value="16:00">4:00 PM</option>
                <option value="16:30">4:30 PM</option>
            </select>
        </div>
    </div>

    <div style="margin-bottom: 1.5rem;">
        <label for="reason" style="display:block; margin-bottom:0.5rem; font-weight:600; color:#2c3e50;">Reason for Visit</label>
        <textarea id="reason" name="reason"
            rows="4"
            style="width:100%; padding:0.75rem 1rem; font-size:1rem; border:1px solid #ccc; border-radius:4px;"></textarea>
    </div>

    <div style="background-color:#f9f9f9; border-left:4px solid #3498db; padding:1rem; border-radius:4px; margin-bottom:1.5rem;">
        <strong style="display:block; margin-bottom:0.5rem; color:#2c3e50;">Important Information</strong>
        <span style="font-size:0.9rem; color:#555;">Please arrive 15 minutes before your scheduled appointment. If you need to cancel or reschedule, please do so at least 24 hours in advance to avoid a cancellation fee.</span>
    </div>

    <div style="display: flex; justify-content: flex-end; gap: 1rem;">
        <button type="button"
            onclick="window.location.href='view_appointments.jsp'"
            style="padding: 0.75rem 1.5rem; font-size:1rem; font-weight:600; background-color:#e0e0e0; color:#333; border:none; border-radius:4px; cursor:pointer;">
            Cancel
        </button>
        <button type="submit"
            style="padding: 0.75rem 1.5rem; font-size:1rem; font-weight:600; background-color:#3498db; color:#fff; border:none; border-radius:4px; cursor:pointer;">
            Book Appointment
        </button>
    </div>
</form>
