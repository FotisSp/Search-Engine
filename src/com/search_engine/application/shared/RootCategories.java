package com.search_engine.application.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class RootCategories implements Serializable {
	private final static String[] PRIMARYCATEGORIES = { "Active Life", "Arts & Entertainment", "Automotive",
			"Beauty & Spas", "Education", "Event Planning & Services", "Financial Services", "Food", "Health & Medical",
			"Home Services", "Hotels & Travel", "Local Flavor", "Local Services", "Mass Media", "Nightlife", "Pets",
			"Professional Services,", "Public Services & Government", "Real Estate", "Religious Organizations",
			"Restaurants", "Shopping" };
	private final static String[] SECONDARYCATEGORIES = { "Amateur Sports Teams", "Amusement Parks", "Aquariums",
			"Archery", "Badminton", "Basketball Courts", "Beaches", "Bike Rentals", "Boating", "Bowling", "Climbing",
			"Disc Golf", "Diving", "Fishing", "Fitness & Instruction", "Go Karts", "Golf", "Gun/Rifle Ranges",
			"Gymnastics", "Hang Gliding", "Hiking", "Horse Racing", "Horseback Riding", "Hot Air Balloons",
			"Kiteboarding", "Lakes", "Laser Tag", "Leisure Centers", "Mini Golf", "Mountain Biking", "Paddleboarding",
			"Paintball", "Parks", "Playgrounds", "Rafting/Kayaking", "Recreation Centers", "Rock Climbing",
			"Skating Rinks", "Skydiving", "Soccer", "Spin Classes", "Sports Clubs", "Squash", "Summer Camps", "Surfing",
			"Swimming Pools", "Tennis", "Trampoline Parks", "Tubing", "Zoos", "Arcades", "Art Galleries",
			"Botanical Gardens", "Casinos", "Cinema", "Cultural Center", "Festivals", "Jazz & Blues", "Museums",
			"Music Venues", "Opera & Ballet", "Performing Arts", "Professional Sports Teams", "Psychics & Astrologers",
			"Race Tracks", "Social Clubs", "Stadiums & Arenas", "Ticket Sales", "Wineries", "Auto Detailing",
			"Auto Glass Services", "Auto Loan Providers", "Auto Parts & Supplies", "Auto Repair", "Boat Dealers",
			"Body Shops", "Car Dealers", "Car Stereo Installation", "Car Wash", "Gas & Service Stations",
			"Motorcycle Dealers", "Motorcycle Repair", "Oil Change Stations", "Parking", "RV Dealers",
			"Smog Check Stations", "Tires", "Towing", "Truck Rental", "Windshield Installation & Repair", "Barbers",
			"Cosmetics & Beauty Supply", "Day Spas", "Eyelash Service", "Hair Extensions", "Hair Removal",
			"Hair Salons", "Makeup Artists", "Massage", "Medical Spas", "Nail Salons", "Permanent Makeup", "Piercing",
			"Rolfing", "Skin care", "Tanning", "Tattoo", "Adult Education", "College Counseling",
			"Colleges & Universities", "Educational Services", "Elementary Schools", "Middle Schools & High Schools",
			"Preschools", "Private Tutors", "Religious Schools", "Special Education", "Specialty Schools",
			"Test Preparation", "Tutoring Centers", "Bartenders", "Boat Charters", "Cards & Stationery", "Caterers",
			"Clowns", "DJs", "Hotels", "Magicians", "Musicians", "Officiants", "Part & Event Planning",
			"Party Bus Rentals", "Party Equipment Rentals", "Party Supplies", "Personal Chefs", "Photographers",
			"Venues & Event Spaces", "Videographers", "Wedding Planning", "Banks & Credit Unions",
			"Check Cashing/Pay-day Loans", "Financial Advising", "Insurance", "Investing", "Tax Services", "Bagels",
			"Bakeries", "Beer, Wine & Spirits", "Breweries", "Bubble Tea", "Butcher", "CSA", "Coffee & Tea",
			"Convenience Stores", "Desserts", "Do-It-Yourself Food", "Donuts", "Farmers Market",
			"Food Delivery Services", "Food Trucks", "Gelato", "Grocery", "Ice Cream & Frozen Yogust", "Internet Cafes",
			"Juice Bars & Smoothies", "Pretzels", "Shaved Ice", "Specialty Food", "Street Vendors", "Tea Rooms",
			"Wineries", "Acupuncture", "Cannabis Clinics", "Chiropractors", "Counseling & Mental Health", "Dentists",
			"Diagnostic Services", "Doctors", "Hearing Aid Providers", "Home Health Care", "Hospice", "Hospitals",
			"Lactation Services", "Laser Eye Surgery/Lasik", "Massage Therapy", "Medical Centers", "Medical Spas",
			"Medical Transportation", "Midwives", "Nutritionists", "Occupational Therapy", "Optometrists",
			"Physical Therapy", "Reflexology", "Rehabilitation Center", "Retirement Homes", "Speech Therapists",
			"Traditional Chinese Medicine", "Urgent Care", "Weight Loss Centers", "Building Supplies",
			"Carpet Installation", "Carpeting", "Contractors", "Damage Restoration", "Electricians", "Flooring",
			"Garage Door Services", "Gardeners", "Handyman", "Heating & Air Conditioning/HVAC", "Home Cleaning",
			"Home Inspectors", "Home Organization", "Home Theatre Installation", "Home Window Tinting",
			"Interior Design", "Internet Service Providers", "Irrigation", "Keys & Locksmith", "Landscape Architects",
			"Landscaping", "Lighting Fixtures & Equipment", "Masonry/Concrete", "Movers", "Painters", "Plumbing",
			"Pool Cleaners", "Real Estate", "Roofing", "Security Systems", "Shades & Blinds", "Solar Installation",
			"Television Service Providers", "Tree Services", "Utilities", "Window Washing", "Windows Installation",
			"Airports", "Bed & Breakfast", "Campgrounds", "Car Rental", "Guest Houses", "Hostels", "Hotels",
			"Motorcycle Rental", "RV Parks", "RV Rental", "Resorts", "Ski Resorts", "Tours", "Train Stations",
			"Transportation", "Travel Services", "Vacation Rental Agents", "Vacation Rentals", "Yelp Events",
			"Appliances & Repair", "Bail Bondsmen", "Bike Repair/Maintenance", "Carpet Cleaning",
			"Child Care & Day Care", "Community Service/Non-Profit", "Couriers & Delivery Services",
			"Dry Cleaning & Laundry", "Electronics Repair", "Funeral Services & Cemeteries", "Furniture Reupholstery",
			"IT Services & Computer Repair", "Jewelry Repair", "Junk Removal & Hauling", "Nanny Services", "Notaries",
			"Pest Control", "Printing Services", "Recording & Rehearsal Studios", "Recycling Center", "Screen Printing",
			"Screen Printing/T-Shirt Printing", "Self Storage", "Sewing & Alterations", "Shipping Centers",
			"Shoe Repair", "Snow Removal", "Watch Repair", "Print Media", "Radio Stations", "Television Stations",
			"Adult Entertainment", "Bars", "Comedy Clubs", "Country Dance Halls", "Dance Clubs", "Jazz & Blues",
			"Karaoke", "Music Venues", "Piano Bars", "Pool Halls", "Animal Shelters", "Horse Boarding", "Pet Services",
			"Pet Stores", "Veterinarians", "Accountants", "Advertising", "Architects", "Boat Repair",
			"Career Counseling", "Editorial Services", "Employment Agencies", "Graphic Design",
			"Internet Service Providers", "Lawyers", "Legal Services", "Life Coach", "Marketing", "Matchmakers",
			"Office Cleaning", "Payroll Services", "Personal Assistants", "Private Investigation", "Public Relations",
			"Talent Agencies", "Taxidermy", "Translation Services", "Video/Film Production", "Web Design",
			"Courthouses", "Departments of Motor Vehicles", "Embassy", "Fire Departments",
			"Landmarks & Historical Buildings", "Libraries", "Police Departments", "Post Offices", "Apartments",
			"Commercial Real Estate", "Home Staging", "Mortgage Brokers", "Property Management", "Real Estate Agents",
			"Real Estate Services", "Shared Office Spaces", "University Housing", "Buddhist Temples", "Churches",
			"Hindu Temples", "Mosques", "Synagogues", "Afghan", "African", "American (New)", "American (Traditional)",
			"Arabian", "Argentine", "Armenian", "Asian Fusion", "Australian", "Austrian", "Bangladeshi", "Barbeque",
			"Basque", "Belgian", "Brasseries", "Brazilian", "Breakfast & Brunch", "British", "Buffets", "Burgers",
			"Burmese", "Cafes", "Cafeteria", "Cajun/Creole", "Cambodian", "Caribbean", "Catalan", "Cheesesteaks",
			"Chicken Wings", "Chinese", "Comfort Food", "Creperies", "Cuban", "Czech", "Delis", "Diners", "Ethiopian",
			"Fast Food", "Filipino", "Fish & Chips", "Fondue", "Food Court", "Food Stands", "French", "Gastropubs",
			"German", "Gluten-Free", "Greek", "Halal", "Hawaiian", "Himalayan/Nepalese", "Hot Dogs", "Hot Pot",
			"Hungarian", "Iberian", "Indian", "Indonesian", "Irish", "Italian", "Japanese", "Korean", "Kosher",
			"Laotian", "Latin American", "Live/Raw Food", "Malaysian", "Meditteranean", "Mexican", "Middle Eastern",
			"Modern European", "Mongolian", "Pakistani", "Persian/Iranian", "Peruvian", "Pizza", "Polish", "Portuguese",
			"Russian", "Salad", "Sandwiches", "Scandinavian", "Scottish", "Seafood", "Singaporean", "Slovakian",
			"Soul Food", "Soup", "Southern", "Spanish", "Steakhouses", "Sushi Bars", "Taiwanese", "Tapas Bars",
			"Tapas/Small Plates", "Tex-Mex", "Thai", "Turkish", "Ukranian", "Vegan", "Vegetarian", "Vietnamese",
			"Adult", "Antiques", "Art Galleries", "Arts & Crafts", "Auction Houses", "Baby Gear & Furniture",
			"Bespoke Clothing", "Books, Mags, Music & Video", "Bridal", "Computers", "Cosmetics & Beauty Supply",
			"Department Stores", "Discount Store", "Drugstores", "Electronics Repair", "Eyewear & Opticians", "Fashion",
			"Fireworks", "Flea Markets", "Flowers & Gifts", "Golf Equipment Shops", "Guns & Ammo", "Hobby Shops",
			"Home & Garden", "Jewelry", "Knitting Supplies", "Luggage", "Medical Supplies", "Mobile Phones",
			"Motorcycle Gear", "Musical Instruments & Teachers", "Office Equipment", "Outlet Stores", "Pawn Shops",
			"Personal Shopping", "Photography Stores & Services", "Pool & Billiards", "Pop-up Shops",
			"Shopping Centers", "Sporting Goods", "Thrift Stores", "Tobacco Shops", "Toy Stores", "Trophy Shops",
			"Uniforms", "Watches", "Wholesale Stores", "Wigs" };
	private final static String[] TERTIARYCATEGORIES = { "Diving", "Fitness & Instruction", "Parks", "Hair Removal",
			"Hair Salons", "Tanning", "Specialty Schools", "Photographers", "Specialty Food", "Dentists",
			"Diagnostic Services", "Doctors", "Real Estate", "Transportation", "IT Services & Computer Repair", "Bars",
			"Pet Services", "Lawyers", "African", "Caribbean", "Chinese", "Latin American", "Middle Eastern",
			"Arts & Crafts", "Books, Mags, Music & Video", "Fashion", "Flowers & Gifts", "Home & Garden",
	"Sporting Goods" };
	private final static String[] ACTIVELIFE = { "Amateur Sports Teams", "Amusement Parks", "Aquariums", "Archery",
			"Badminton", "Basketball Courts", "Beaches", "Bike Rentals", "Boating", "Bowling", "Climbing", "Disc Golf",
			"Diving", "Fishing", "Fitness & Instruction", "Go Karts", "Golf", "Gun/Rifle Ranges", "Gymnastics",
			"Hang Gliding", "Hiking", "Horse Racing", "Horseback Riding", "Hot Air Balloons", "Kiteboarding", "Lakes",
			"Laser Tag", "Leisure Centers", "Mini Golf", "Mountain Biking", "Paddleboarding", "Paintball", "Parks",
			"Playgrounds", "Rafting/Kayaking", "Recreation Centers", "Rock Climbing", "Skating Rinks", "Skydiving",
			"Soccer", "Spin Classes", "Sports Clubs", "Squash", "Summer Camps", "Surfing", "Swimming Pools", "Tennis",
			"Trampoline Parks", "Tubing", "Zoos" };
	private final static String[] ARTS = { "Arcades", "Art Galleries", "Botanical Gardens", "Casinos", "Cinema",
			"Cultural Center", "Festivals", "Jazz & Blues", "Museums", "Music Venues", "Opera & Ballet",
			"Performing Arts", "Professional Sports Teams", "Psychics & Astrologers", "Race Tracks", "Social Clubs",
			"Stadiums & Arenas", "Ticket Sales", "Wineries" };
	private final static String[] AUTOMOTIVE = { "Auto Detailing", "Auto Glass Services", "Auto Loan Providers",
			"Auto Parts & Supplies", "Auto Repair", "Boat Dealers", "Body Shops", "Car Dealers",
			"Car Stereo Installation", "Car Wash", "Gas & Service Stations", "Motorcycle Dealers", "Motorcycle Repair",
			"Oil Change Stations", "Parking", "RV Dealers", "Smog Check Stations", "Tires", "Towing", "Truck Rental",
	"Windshield Installation & Repair" };
	private final static String[] BEAUTY = { "Barbers", "Cosmetics & Beauty Supply", "Day Spas", "Eyelash Service",
			"Hair Extensions", "Hair Removal", "Hair Salons", "Makeup Artists", "Massage", "Medical Spas",
			"Nail Salons", "Permanent Makeup", "Piercing", "Rolfing", "Skin care", "Tanning", "Tattoo" };
	private final static String[] EDUCATION = { "Adult Education", "College Counseling", "Colleges & Universities",
			"Educational Services", "Elementary Schools", "Middle Schools & High Schools", "Preschools",
			"Private Tutors", "Religious Schools", "Special Education", "Specialty Schools", "Test Preparation",
	"Tutoring Centers" };
	private final static String[] EVENT = { "Bartenders", "Boat Charters", "Cards & Stationery", "Caterers", "Clowns",
			"DJs", "Hotels", "Magicians", "Musicians", "Officiants", "Part & Event Planning", "Party Bus Rentals",
			"Party Equipment Rentals", "Party Supplies", "Personal Chefs", "Photographers", "Venues & Event Spaces",
			"Videographers", "Wedding Planning" };
	private final static String[] FINANCIAL = { "Banks & Credit Unions", "Check Cashing/Pay-day Loans",
			"Financial Advising", "Insurance", "Investing", "Tax Services" };
	private final static String[] FOOD = { "Bagels", "Bakeries", "Beer, Wine & Spirits", "Breweries", "Bubble Tea",
			"Butcher", "CSA", "Coffee & Tea", "Convenience Stores", "Desserts", "Do-It-Yourself Food", "Donuts",
			"Farmers Market", "Food Delivery Services", "Food Trucks", "Gelato", "Grocery", "Ice Cream & Frozen Yogust",
			"Internet Cafes", "Juice Bars & Smoothies", "Pretzels", "Shaved Ice", "Specialty Food", "Street Vendors",
			"Tea Rooms", "Wineries" };
	private final static String[] HEALTH = { "Acupuncture", "Cannabis Clinics", "Chiropractors",
			"Counseling & Mental Health", "Dentists", "Diagnostic Services", "Doctors", "Hearing Aid Providers",
			"Home Health Care", "Hospice", "Hospitals", "Lactation Services", "Laser Eye Surgery/Lasik",
			"Massage Therapy", "Medical Centers", "Medical Spas", "Medical Transportation", "Midwives", "Nutritionists",
			"Occupational Therapy", "Optometrists", "Physical Therapy", "Reflexology", "Rehabilitation Center",
			"Retirement Homes", "Speech Therapists", "Traditional Chinese Medicine", "Urgent Care",
	"Weight Loss Centers" };
	private final static String[] HOMESERVICE = { "Building Supplies", "Carpet Installation", "Carpeting",
			"Contractors", "Damage Restoration", "Electricians", "Flooring", "Garage Door Services", "Gardeners",
			"Handyman", "Heating & Air Conditioning/HVAC", "Home Cleaning", "Home Inspectors", "Home Organization",
			"Home Theatre Installation", "Home Window Tinting", "Interior Design", "Internet Service Providers",
			"Irrigation", "Keys & Locksmith", "Landscape Architects", "Landscaping", "Lighting Fixtures & Equipment",
			"Masonry/Concrete", "Movers", "Painters", "Plumbing", "Pool Cleaners", "Real Estate", "Roofing",
			"Security Systems", "Shades & Blinds", "Solar Installation", "Television Service Providers",
			"Tree Services", "Utilities", "Window Washing", "Windows Installation" };
	private final static String[] HOTELTRAVEL = { "Airports", "Bed & Breakfast", "Campgrounds", "Car Rental",
			"Guest Houses", "Hostels", "Hotels", "Motorcycle Rental", "RV Parks", "RV Rental", "Resorts", "Ski Resorts",
			"Tours", "Train Stations", "Transportation", "Travel Services", "Vacation Rental Agents",
	"Vacation Rentals" };
	private final static String[] LOCALFLAVOR = { "Yelp Events" };
	private final static String[] LOCALSERVICES = { "Appliances & Repair", "Bail Bondsmen", "Bike Repair/Maintenance",
			"Carpet Cleaning", "Child Care & Day Care", "Community Service/Non-Profit", "Couriers & Delivery Services",
			"Dry Cleaning & Laundry", "Electronics Repair", "Funeral Services & Cemeteries", "Furniture Reupholstery",
			"IT Services & Computer Repair", "Jewelry Repair", "Junk Removal & Hauling", "Nanny Services", "Notaries",
			"Pest Control", "Printing Services", "Recording & Rehearsal Studios", "Recycling Center", "Screen Printing",
			"Screen Printing/T-Shirt Printing", "Self Storage", "Sewing & Alterations", "Shipping Centers",
			"Shoe Repair", "Snow Removal", "Watch Repair" };
	private final static String[] MASSMEDIA = { "Print Media", "Radio Stations", "Television Stations" };
	private final static String[] NIGHTLIFE = { "Adult Entertainment", "Bars", "Comedy Clubs", "Country Dance Halls",
			"Dance Clubs", "Jazz & Blues", "Karaoke", "Music Venues", "Piano Bars", "Pool Halls" };
	private final static String[] PETS = { "Animal Shelters", "Horse Boarding", "Pet Services", "Pet Stores",
	"Veterinarians" };
	private final static String[] PROFSERVICES = { "Accountants", "Advertising", "Architects", "Boat Repair",
			"Career Counseling", "Editorial Services", "Employment Agencies", "Graphic Design",
			"Internet Service Providers", "Lawyers", "Legal Services", "Life Coach", "Marketing", "Matchmakers",
			"Office Cleaning", "Payroll Services", "Personal Assistants", "Private Investigation", "Public Relations",
			"Talent Agencies", "Taxidermy", "Translation Services", "Video/Film Production", "Web Design" };
	private final static String[] PUBLICSERVICES = { "Courthouses", "Departments of Motor Vehicles", "Embassy",
			"Fire Departments", "Landmarks & Historical Buildings", "Libraries", "Police Departments", "Post Offices" };
	private final static String[] REALESTATE = { "Apartments", "Commercial Real Estate", "Home Staging",
			"Mortgage Brokers", "Property Management", "Real Estate Agents", "Real Estate Services",
			"Shared Office Spaces", "University Housing" };
	private final static String[] RELIGIOUS = { "Buddhist Temples", "Churches", "Hindu Temples", "Mosques",
	"Synagogues" };
	private final static String[] RESTAURANTS = { "Afghan", "African", "American (New)", "American (Traditional)",
			"Arabian", "Argentine", "Armenian", "Asian Fusion", "Australian", "Austrian", "Bangladeshi", "Barbeque",
			"Basque", "Belgian", "Brasseries", "Brazilian", "Breakfast & Brunch", "British", "Buffets", "Burgers",
			"Burmese", "Cafes", "Cafeteria", "Cajun/Creole", "Cambodian", "Caribbean", "Catalan", "Cheesesteaks",
			"Chicken Wings", "Chinese", "Comfort Food", "Creperies", "Cuban", "Czech", "Delis", "Diners", "Ethiopian",
			"Fast Food", "Filipino", "Fish & Chips", "Fondue", "Food Court", "Food Stands", "French", "Gastropubs",
			"German", "Gluten-Free", "Greek", "Halal", "Hawaiian", "Himalayan/Nepalese", "Hot Dogs", "Hot Pot",
			"Hungarian", "Iberian", "Indian", "Indonesian", "Irish", "Italian", "Japanese", "Korean", "Kosher",
			"Laotian", "Latin American", "Live/Raw Food", "Malaysian", "Meditteranean", "Mexican", "Middle Eastern",
			"Modern European", "Mongolian", "Pakistani", "Persian/Iranian", "Peruvian", "Pizza", "Polish", "Portuguese",
			"Russian", "Salad", "Sandwiches", "Scandinavian", "Scottish", "Seafood", "Singaporean", "Slovakian",
			"Soul Food", "Soup", "Southern", "Spanish", "Steakhouses", "Sushi Bars", "Taiwanese", "Tapas Bars",
			"Tapas/Small Plates", "Tex-Mex", "Thai", "Turkish", "Ukranian", "Vegan", "Vegetarian", "Vietnamese" };
	private final static String[] SHOPPING = { "Adult", "Antiques", "Art Galleries", "Arts & Crafts", "Auction Houses",
			"Baby Gear & Furniture", "Bespoke Clothing", "Books, Mags, Music & Video", "Bridal", "Computers",
			"Cosmetics & Beauty Supply", "Department Stores", "Discount Store", "Drugstores", "Electronics Repair",
			"Eyewear & Opticians", "Fashion", "Fireworks", "Flea Markets", "Flowers & Gifts", "Golf Equipment Shops",
			"Guns & Ammo", "Hobby Shops", "Home & Garden", "Jewelry", "Knitting Supplies", "Luggage",
			"Medical Supplies", "Mobile Phones", "Motorcycle Gear", "Musical Instruments & Teachers",
			"Office Equipment", "Outlet Stores", "Pawn Shops", "Personal Shopping", "Photography Stores & Services",
			"Pool & Billiards", "Pop-up Shops", "Shopping Centers", "Sporting Goods", "Thrift Stores", "Tobacco Shops",
			"Toy Stores", "Trophy Shops", "Uniforms", "Watches", "Wholesale Stores", "Wigs" };

	private List<String> primaryCategories = new ArrayList<String>(Arrays.asList(PRIMARYCATEGORIES));
	private List<String> secondaryCategories = new ArrayList<String>(Arrays.asList(SECONDARYCATEGORIES));

	private HashMap<String, List<String>> secondary = new HashMap<>();
	private HashMap<String, List<String>> tertiary = new HashMap<>();

	private final static String[] DIVING = { "Free Diving", "Scuba Diving" };
	private final static String[] FITNESS = { "Barre Classes", "Boot Camps", "Boxing", "Dance Studios", "Gyms",
			"Martial Arts", "Pilates", "Swimming Lessons/Schools", "Tai Chi", "Trainers", "Yoga" };
	private final static String[] PARKS = { "Dog Parks", "Skate Parks" };
	private final static String[] HAIRREMOVAL = { "Laser Hair Removal" };
	private final static String[] HAIRSALONS = { "Blow Dry/Out Services", "Hair Extensions", "Hair Stylists",
	"Men's Hair Salons" };
	private final static String[] TANNING = { "Spray Tanning", "Tanning Beds" };
	private final static String[] SCHOOLS = { "Art Schools", "CPR Classes", "Cooking Schools", "Cosmetology Schools",
			"Dance Schools", "Driving Schools", "First Aid Classes", "Flight Instruction", "Language Schools",
			"Massage Schools", "Swimming Lessons/Schools", "Vocational & Technical School" };
	private final static String[] PHOTOGRAPHERS = { "Event Photography", "Session Photography" };
	private final static String[] SPECIALITYFOOD = { "Candy Stores", "Cheese Shops", "Chocolatiers & Shops",
			"Ethnic Food", "Fruits & Veggies", "Health Markets", "Herbs & Spices", "Meat Shops", "Seafood Markets" };
	private final static String[] DENTISTS = { "Cosmetic Dentists", "Endodontists", "General Dentistry",
			"Oral Surgeons", "Orthodontists", "Pediatric Dentists", "Periodontists" };
	private final static String[] DIAGNOSTICS = { "Diagnostic Imaging", "Laboratory Testing" };
	private final static String[] DOCTORS = { "Allergists", "Anesthesiologists", "Audiologist", "Cardiologists",
			"Cosmetic Surgeons", "Dermatologists", "Ear Nose & Throat", "Family Practice", "Fertility",
			"Gastroenterologist", "Gerontologists", "Internal Medicine", "Naturopathic/Holistic", "Neurologist",
			"Obstretricians & Gynecologists", "Oncologist", "Ophthalmologists", "Orthopedists",
			"Osteopathic Physicians", "Pediatricians", "Podiatrists", "Proctologists", "Psychiatrists",
			"Pulmonologists", "Sports Medicine", "Tattoo Removal", "Urologists" };
	private final static String[] REALE_STATE = { "Apartments", "Commercial Real Estate", "Home Staging",
			"Mortgage Brokers", "Property Management", "Real Estate Agents", "Real Estate Services",
			"Shared Office Spaces", "University Housing" };
	private final static String[] TRANSPORT = { "Airlines", "Airport Shuttles", "Limos", "Public Transportation",
	"Taxis" };
	private final static String[] ITSERVICES = { "Data Recovery", "Mobile Phone Repair" };
	private final static String[] BARS = { "Champagne Bars", "Cocktail Bars", "Dive Bars", "Gay Bars", "Hookah Bars",
			"Lounges", "Pubs", "Sports Bars", "Wine Bars" };
	private final static String[] PETSERVICES = { "Dog Walkers", "Pet Boarding/Pet Sitting", "Pet Groomers",
	"Pet Training" };
	private final static String[] LAWYERS = { "Bankruptcy Law", "Business Law", "Criminal Defense Law", "DUI Law",
			"Divorce & Family Law", "Employment Law", "Estate Planning Law", "General Litigation", "Immigration Law",
			"Personal Injury Law", "Real Estate Law" };
	private final static String[] AFRICAN = { "Senegalese", "South African" };
	private final static String[] CARIBBEAN = { "Dominican", "Haitian", "Puerto Rican", "Trinidadian" };
	private final static String[] CHINESE = { "Cantonese", "Dim Sum", "Shanghainese", "Szechuan" };
	private final static String[] LATINAMERICA = { "Columbian", "Salvadoran", "Venezuelan" };
	private final static String[] MIDDLEEAST = { "Egyptian", "Lebanese" };
	private final static String[] ARTSANDCRAFTS = { "Art Supplies", "Cards & Stationery", "Costumes", "Fabric Stores",
	"Framing" };
	private final static String[] BOOKS = { "Bookstores", "Comic Books", "Music & DVDs", "Newspapers & Magazines",
			"Videos & Video Game Rental", "Vinyl Records" };
	private final static String[] FASHION = { "Accessories", "Children's Clothing", "Department Stores", "Formal Wear",
			"Hats", "Leather Goods", "Lingerie", "Maternity Wear", "Men's Clothing", "Plus Size Fashion", "Shoe Stores",
			"Sports Wear", "Surf Shop", "Swimwear", "Used, Vintage & Consignment", "Women's Clothing" };
	private final static String[] GIFTS = { "Cards & Stationery", "Florists", "Gift Shops" };
	private final static String[] HOMEGARDEN = { "Appliances", "Furniture Stores", "Hardware Stores", "Home D�cor",
			"Hot Tub & Pool", "Kitchen & Bath", "Mattresses", "Nurseries & Gardening" };
	private final static String[] SPORTS = { "Bikes", "Golf Equipment", "Outdoor Gear", "Sports Wear" };

	private String name;
	private List<ChildCategories> cityStateStars = new ArrayList<>();
	private List<Categories> categories = new ArrayList<>();

	public void construct() {
		List<String> activeLife = new ArrayList<String>(Arrays.asList(ACTIVELIFE));
		List<String> arts = new ArrayList<String>(Arrays.asList(ARTS));
		List<String> automotive = new ArrayList<String>(Arrays.asList(AUTOMOTIVE));
		List<String> beauty = new ArrayList<String>(Arrays.asList(BEAUTY));
		List<String> education = new ArrayList<String>(Arrays.asList(EDUCATION));
		List<String> event = new ArrayList<String>(Arrays.asList(EVENT));
		List<String> financial = new ArrayList<String>(Arrays.asList(FINANCIAL));
		List<String> food = new ArrayList<String>(Arrays.asList(FOOD));
		List<String> health = new ArrayList<String>(Arrays.asList(HEALTH));
		List<String> homeService = new ArrayList<String>(Arrays.asList(HOMESERVICE));
		List<String> hotelTravel = new ArrayList<String>(Arrays.asList(HOTELTRAVEL));
		List<String> localFlavor = new ArrayList<String>(Arrays.asList(LOCALFLAVOR));
		List<String> localServices = new ArrayList<String>(Arrays.asList(LOCALSERVICES));
		List<String> massMedia = new ArrayList<String>(Arrays.asList(MASSMEDIA));
		List<String> nightLife = new ArrayList<String>(Arrays.asList(NIGHTLIFE));
		List<String> pets = new ArrayList<String>(Arrays.asList(PETS));
		List<String> profServices = new ArrayList<String>(Arrays.asList(PROFSERVICES));
		List<String> publicServices = new ArrayList<String>(Arrays.asList(PUBLICSERVICES));
		List<String> realEstate = new ArrayList<String>(Arrays.asList(REALESTATE));
		List<String> religious = new ArrayList<String>(Arrays.asList(RELIGIOUS));
		List<String> restaurants = new ArrayList<String>(Arrays.asList(RESTAURANTS));
		List<String> shopping = new ArrayList<String>(Arrays.asList(SHOPPING));

		secondary.put(PRIMARYCATEGORIES[0], activeLife);
		secondary.put(PRIMARYCATEGORIES[1], arts);
		secondary.put(PRIMARYCATEGORIES[2], automotive);
		secondary.put(PRIMARYCATEGORIES[3], beauty);
		secondary.put(PRIMARYCATEGORIES[4], education);
		secondary.put(PRIMARYCATEGORIES[5], event);
		secondary.put(PRIMARYCATEGORIES[6], financial);
		secondary.put(PRIMARYCATEGORIES[7], food);
		secondary.put(PRIMARYCATEGORIES[8], health);
		secondary.put(PRIMARYCATEGORIES[9], homeService);
		secondary.put(PRIMARYCATEGORIES[10], hotelTravel);
		secondary.put(PRIMARYCATEGORIES[11], localFlavor);
		secondary.put(PRIMARYCATEGORIES[12], localServices);
		secondary.put(PRIMARYCATEGORIES[13], massMedia);
		secondary.put(PRIMARYCATEGORIES[14], nightLife);
		secondary.put(PRIMARYCATEGORIES[15], pets);
		secondary.put(PRIMARYCATEGORIES[16], profServices);
		secondary.put(PRIMARYCATEGORIES[17], publicServices);
		secondary.put(PRIMARYCATEGORIES[18], realEstate);
		secondary.put(PRIMARYCATEGORIES[19], religious);
		secondary.put(PRIMARYCATEGORIES[20], restaurants);
		secondary.put(PRIMARYCATEGORIES[21], shopping);

		// Tertiary
		List<String> diving = new ArrayList<String>(Arrays.asList(DIVING));
		List<String> fitness = new ArrayList<String>(Arrays.asList(FITNESS));
		List<String> parks = new ArrayList<String>(Arrays.asList(PARKS));
		List<String> hairRemoval = new ArrayList<String>(Arrays.asList(HAIRREMOVAL));
		List<String> hairSalons = new ArrayList<String>(Arrays.asList(HAIRSALONS));
		List<String> tanning = new ArrayList<String>(Arrays.asList(TANNING));
		List<String> schools = new ArrayList<String>(Arrays.asList(SCHOOLS));
		List<String> photographers = new ArrayList<String>(Arrays.asList(PHOTOGRAPHERS));
		List<String> specialityFood = new ArrayList<String>(Arrays.asList(SPECIALITYFOOD));
		List<String> dentists = new ArrayList<String>(Arrays.asList(DENTISTS));
		List<String> diagnostics = new ArrayList<String>(Arrays.asList(DIAGNOSTICS));
		List<String> doctors = new ArrayList<String>(Arrays.asList(DOCTORS));
		List<String> realState = new ArrayList<String>(Arrays.asList(REALE_STATE));
		List<String> transport = new ArrayList<String>(Arrays.asList(TRANSPORT));
		List<String> itServices = new ArrayList<String>(Arrays.asList(ITSERVICES));
		List<String> bars = new ArrayList<String>(Arrays.asList(BARS));
		List<String> petServices = new ArrayList<String>(Arrays.asList(PETSERVICES));
		List<String> lawyers = new ArrayList<String>(Arrays.asList(LAWYERS));
		List<String> african = new ArrayList<String>(Arrays.asList(AFRICAN));
		List<String> caribbean = new ArrayList<String>(Arrays.asList(CARIBBEAN));
		List<String> chinese = new ArrayList<String>(Arrays.asList(CHINESE));
		List<String> latinAmerica = new ArrayList<String>(Arrays.asList(LATINAMERICA));
		List<String> middleEast = new ArrayList<String>(Arrays.asList(MIDDLEEAST));
		List<String> artsAndCrafts = new ArrayList<String>(Arrays.asList(ARTSANDCRAFTS));
		List<String> books = new ArrayList<String>(Arrays.asList(BOOKS));
		List<String> fashion = new ArrayList<String>(Arrays.asList(FASHION));
		List<String> gifts = new ArrayList<String>(Arrays.asList(GIFTS));
		List<String> homeGarden = new ArrayList<String>(Arrays.asList(HOMEGARDEN));
		List<String> sports = new ArrayList<String>(Arrays.asList(SPORTS));

		// tertiary hashmap
		tertiary.put(TERTIARYCATEGORIES[0], diving);
		tertiary.put(TERTIARYCATEGORIES[1], fitness);
		tertiary.put(TERTIARYCATEGORIES[2], parks);
		tertiary.put(TERTIARYCATEGORIES[3], hairRemoval);
		tertiary.put(TERTIARYCATEGORIES[4], hairSalons);
		tertiary.put(TERTIARYCATEGORIES[5], tanning);
		tertiary.put(TERTIARYCATEGORIES[6], schools);
		tertiary.put(TERTIARYCATEGORIES[7], photographers);
		tertiary.put(TERTIARYCATEGORIES[8], specialityFood);
		tertiary.put(TERTIARYCATEGORIES[9], dentists);
		tertiary.put(TERTIARYCATEGORIES[10], diagnostics);
		tertiary.put(TERTIARYCATEGORIES[11], doctors);
		tertiary.put(TERTIARYCATEGORIES[12], realState);
		tertiary.put(TERTIARYCATEGORIES[13], transport);
		tertiary.put(TERTIARYCATEGORIES[14], itServices);
		tertiary.put(TERTIARYCATEGORIES[15], bars);
		tertiary.put(TERTIARYCATEGORIES[16], petServices);
		tertiary.put(TERTIARYCATEGORIES[17], lawyers);
		tertiary.put(TERTIARYCATEGORIES[18], african);
		tertiary.put(TERTIARYCATEGORIES[19], caribbean);
		tertiary.put(TERTIARYCATEGORIES[20], chinese);
		tertiary.put(TERTIARYCATEGORIES[21], latinAmerica);
		tertiary.put(TERTIARYCATEGORIES[22], middleEast);
		tertiary.put(TERTIARYCATEGORIES[23], artsAndCrafts);
		tertiary.put(TERTIARYCATEGORIES[24], books);
		tertiary.put(TERTIARYCATEGORIES[25], fashion);
		tertiary.put(TERTIARYCATEGORIES[26], gifts);
		tertiary.put(TERTIARYCATEGORIES[27], homeGarden);
		tertiary.put(TERTIARYCATEGORIES[28], sports);

		for (int i = 0; i < PRIMARYCATEGORIES.length; i++) {
			Categories newCategory = new Categories();
			newCategory.constructCategories(PRIMARYCATEGORIES[i], 0);
			newCategory.createSubCategory(secondary.get(PRIMARYCATEGORIES[i]));
			categories.add(newCategory);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategories(List<Categories> category) {
		categories = category;
	}

	public void setCityStateStars(List<ChildCategories> child) {
		cityStateStars = child;
	}

	public String getName() {
		return name;
	}

	public List<String> getCityStateStars() {
		List<String> tempList = new ArrayList<>();
		for (int i = 0; i < cityStateStars.size(); i++) {
			tempList.add(cityStateStars.get(i).toString());
		}
		Collections.sort(tempList);
		return tempList;
	}

	public List<Categories> getCategories() {
		return categories;
	}

	public void containsChild(String name) {
		int flag = 0;
		for (int i = 0; i < cityStateStars.size(); i++) {
			if (cityStateStars.get(i).containsName(name)) {
				flag = 1;
				break;
			}
		}
		if (flag == 0) {
			ChildCategories temp = new ChildCategories();
			temp.construct(name, 1);
			cityStateStars.add(temp);
		}
	}

	public void containsCategory(String name) {
		if (secondaryCategories.contains(name)) {
			int primaryName = -1;

			for (int i = 0; i < primaryCategories.size(); i++) {
				if (secondary.get(primaryCategories.get(i)).contains(name)) {
					primaryName = i;
					break;
				}
			}
			if (primaryName != -1) {
				List<Categories> sub = categories.get(primaryName).getSubCategory();
				for (int j = 0; j < sub.size(); j++) {
					if (sub.get(j).contains(name)) {
						categories.get(primaryName).increaseOccurrences();
						categories.get(primaryName).setSubCategoryExistance();
						categories.get(primaryName).setSubCategory(sub);
						break;
					}
				}
			} 
		} 
	}

	public List<Categories> removeEmpty() {
		List<Categories> newCategories = new ArrayList<>();

		for (int i = 0; i < categories.size(); i++) {
			categories.get(i).removeEmpty();
			if (categories.get(i).getOccurrences() > 0) {
				newCategories.add(categories.get(i));
			}
		}
		return newCategories;
	}

	@Override
	public String toString() {
		return "RootCategories [name=" + name + ", categories=" + categories + "]";
	}
}
