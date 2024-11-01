class ApartmentsManager {
    constructor() {
        this.apartmentsContainer = document.querySelector('.row:not(.mb-4)');
        this.searchForm = document.querySelector('form');
        this.init();
    }

    init() {
        this.searchForm.addEventListener('submit', (e) => {
            e.preventDefault();
            this.loadApartments();
        });
        this.loadApartments();
    }

    async loadApartments() {
        try {
            const url = `/api/apartments`;

            this.apartmentsContainer.innerHTML = `
                <div class="col-12 text-center">
                    <div class="spinner-border" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                </div>
            `;

            const response = await fetch(url);
            console.log(response)
            if (!response.ok) {
                throw new Error('Network error');
            }
            const apartments = await response.json();
            this.renderApartments(apartments);
        } catch (error) {
            this.apartmentsContainer.innerHTML = `
                <div class="col-12">
                    <div class="alert alert-danger" role="alert">
                        Error loading apartments. Please try again later.
                    </div>
                </div>
            `;
        }
    }

    renderApartments(apartments) {
        if (apartments.length === 0) {
            this.apartmentsContainer.innerHTML = `
                <div class="col-12">
                    <div class="alert alert-info" role="alert">
                        No apartments found.
                    </div>
                </div>
            `;
            return;
        }

        this.apartmentsContainer.innerHTML = apartments.map(apartment => `
            <div class="col-md-4 mb-4">
                <div class="card apartment-card h-100">
                    <img 
                        src="${apartment.photos && apartment.photos.length > 0
            ? apartment.photos[0].photoUrl
            : '/images/default-apartment.jpg'}"
                        class="card-img-top" 
                        alt="${apartment.title}"
                    >
                    <div class="card-body">
                        <h5 class="card-title">${apartment.title}</h5>
                        <p class="card-text">
                            <i class="fas fa-map-marker-alt"></i>
                            <span>${apartment.address}</span>
                        </p>
                        <p class="card-text">
                            <i class="fas fa-bed"></i> <span>${apartment.bedrooms}</span> bedrooms
                            <i class="fas fa-bath ms-2"></i> <span>${apartment.bathrooms}</span> bathrooms
                        </p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="h5 mb-0">$${apartment.pricePerNight}/night</span>
                            <a href="/apartment/${apartment.id}" class="btn btn-primary">View Details</a>
                        </div>
                    </div>
                </div>
            </div>
        `).join('');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new ApartmentsManager();
});