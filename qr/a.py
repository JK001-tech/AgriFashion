import qrcode
from tkinter import Tk, Label, Entry, Button, PhotoImage
from PIL import Image, ImageTk

# Function to Generate Barcode and Update UI
def generate_barcode():
    global barcode_photo  # Prevent garbage collection

    # Get User Input
    data = {
        "cno": cno_entry.get(),
        "Name": name_entry.get(),
        "Soil Percentage": soil_percentage_entry.get(),
        "Soil Rating": soil_rating_entry.get(),
        "Issue Date": issue_date_entry.get(),
        "Expiry Date": expiry_date_entry.get(),
        "Status": status_entry.get()
    }

    # Generate Barcode
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    qr.add_data(data["cno"])
    qr.make(fit=True)

    img = qr.make_image(fill="black", back_color="white")
    img.save("barcode.png")

    # Display Barcode
    barcode_img = Image.open("barcode.png")
    barcode_img = barcode_img.resize((200, 200), Image.Resampling.LANCZOS)
    barcode_photo = ImageTk.PhotoImage(barcode_img)

    barcode_label.config(image=barcode_photo)
    barcode_label.image = barcode_photo

    # Update Text Labels
    info_label.config(text="\n".join([f"{key}: {value}" for key, value in data.items()]))

# Create GUI
root = Tk()
root.title("License Details")
root.geometry("400x600")

# Input Fields
Label(root, text="Enter Details", font=("Arial", 14, "bold")).pack()

Label(root, text="License Number:").pack()
cno_entry = Entry(root)
cno_entry.pack()

Label(root, text="Name:").pack()
name_entry = Entry(root)
name_entry.pack()

Label(root, text="Soil Percentage:").pack()
soil_percentage_entry = Entry(root)
soil_percentage_entry.pack()

Label(root, text="Soil Rating:").pack()
soil_rating_entry = Entry(root)
soil_rating_entry.pack()

Label(root, text="Issue Date (DD-MM-YYYY):").pack()
issue_date_entry = Entry(root)
issue_date_entry.pack()

Label(root, text="Expiry Date (DD-MM-YYYY):").pack()
expiry_date_entry = Entry(root)
expiry_date_entry.pack()

Label(root, text="Status:").pack()
status_entry = Entry(root)
status_entry.pack()

# Generate Barcode Button
generate_button = Button(root, text="Generate Barcode", command=generate_barcode)
generate_button.pack(pady=10)

# Barcode Display
barcode_label = Label(root)
barcode_label.pack()

# Info Display
info_label = Label(root, text="", font=("Arial", 12), justify="left")
info_label.pack()

root.mainloop()
