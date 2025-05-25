import React, { useCallback, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import styles from './masterDetail.module.css';

function MasterDetailPage() {
  const { id } = useParams();
  const [activity, setActivity] = useState(null);
  const [statuses, setStatuses] = useState([]);
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({});
  const [availableVolunteers, setAvailableVolunteers] = useState([]);
  const [selectedVolunteerId, setSelectedVolunteerId] = useState('');
  const [editLocationMode, setEditLocationMode] = useState(false);
  const [locationForm, setLocationForm] = useState({ city: '', country: '', address: '' });
  const [searchTerm, setSearchTerm] = useState('');

  const getActivity = useCallback(() => {
    axios.get(`http://localhost:8080/api/activity/${id}`)
      .then(response => {
        setActivity(response.data);
        setFormData({
          name: response.data.name,
          statusId: response.data.status.id,
          date: response.data.date.split(' ')[0],
          time: response.data.time
        });
        if (response.data.location) {
          setLocationForm({
            city: response.data.location.city,
            country: response.data.location.country,
            address: response.data.location.address
          });
        }
      })
      .catch(error => console.error('Greška pri dohvaćanju aktivnosti:', error));
  }, [id]);

  const getStatuses = useCallback(() => {
    axios.get(`http://localhost:8080/api/activity-status`)
      .then(response => setStatuses(response.data))
      .catch(error => console.error('Greška pri dohvaćanju statusa:', error));
  }, []);

  const getAvailableVolunteers = useCallback(() => {
    axios.get(`http://localhost:8080/api/volunteers/not-in-activity/${id}`)
      .then(response => setAvailableVolunteers(response.data))
      .catch(error => console.error('Greška pri dohvaćanju dostupnih volontera:', error));
  }, [id]);

  useEffect(() => {
    getActivity();
    getStatuses();
    getAvailableVolunteers();
  }, [id, getActivity, getStatuses, getAvailableVolunteers]);

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSave = () => {
    if (!formData.name.trim() || !formData.statusId || !formData.date || !formData.time) {
      alert('Sva polja moraju biti popunjena!');
      return;
    }
    if (formData.name.length > 255) {
      alert('Naziv ne smije imati više od 255 znakova.');
      return;
    }

    const now = new Date();
    const selectedDate = new Date(formData.date);
    const [hours, minutes] = formData.time.split(':').map(Number);
    selectedDate.setHours(hours, minutes, 0, 0);

    if (selectedDate <= now) {
      alert('Odabrano vrijeme je u prošlosti!');
      return;
    }

    const oneHourLater = new Date(now.getTime() + 60 * 60 * 1000);
    if (now.toDateString() === selectedDate.toDateString() && selectedDate < oneHourLater) {
      alert('Vrijeme mora biti barem 1 sat od sada!');
      return;
    }

    const paddedTime = formData.time.length === 5 ? formData.time + ':00' : formData.time;
    axios.put(`http://localhost:8080/api/activity/${id}`, {
      ...formData,
      time: paddedTime,
      status: { id: formData.statusId }
    })
      .then(() => {
        setEditMode(false);
        getActivity();
      })
      .catch(error => console.error('Greška pri spremanju aktivnosti:', error));
  };

  const handleAddVolunteer = () => {
    if (!selectedVolunteerId) {
      alert('Odaberite volontera!');
      return;
    }
    axios.post(`http://localhost:8080/api/activity/${id}/volunteers/${selectedVolunteerId}`)
      .then(() => {
        setSelectedVolunteerId('');
        getActivity();
        getAvailableVolunteers();
      })
      .catch(error => console.error('Greška pri dodavanju volontera:', error));
  };

  const handleDeleteVolunteer = (volunteerId) => {
    axios.delete(`http://localhost:8080/api/activity/${id}/volunteers/${volunteerId}`)
      .then(() => {
        getActivity();
        getAvailableVolunteers();
      })
      .catch(error => console.error('Greška pri brisanju volontera:', error));
  };

  const handleLocationInputChange = (e) => {
    setLocationForm({ ...locationForm, [e.target.name]: e.target.value });
  };

  const handleSaveLocation = () => {
    if (!locationForm.city.trim() || !locationForm.country.trim() || !locationForm.address.trim()) {
      alert('Sva polja lokacije moraju biti popunjena!');
      return;
    }
    if (locationForm.city.length > 50 || locationForm.country.length > 50 || locationForm.address.length > 200) {
      alert('Grad i država ne smiju imati više od 50 znakova, a adresa najviše 200.');
      return;
    }

    axios.put(`http://localhost:8080/api/activity/${id}/location`, locationForm)
      .then(() => {
        setEditLocationMode(false);
        getActivity();
      })
      .catch(error => console.error('Greška pri spremanju lokacije:', error));
  };

  if (!activity) {
    return <div className={styles.container}>Učitavanje...</div>;
  }

  const filteredVolunteers = activity.volunteers.filter((vol) =>
    `${vol.firstName} ${vol.lastName} ${vol.email}`.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className={styles.container}>
      <h2>Detalji aktivnosti</h2>

      <div className={styles.section}>
        {editMode ? (
          <>
            <input name="name" type="text" value={formData.name} onChange={handleInputChange} placeholder="Naziv" />
            <select name="statusId" value={formData.statusId} onChange={handleInputChange}>
              <option value="">-- Odaberi status --</option>
              {statuses.map((status) => (
                <option key={status.id} value={status.id}>{status.name}</option>
              ))}
            </select>
            <input type="date" name="date" value={formData.date} onChange={handleInputChange} />
            <input type="time" name="time" value={formData.time} onChange={handleInputChange} />
            <button onClick={handleSave}>Spremi</button>
            <button onClick={() => {
              setEditMode(false);
              setFormData({
                name: activity.name,
                statusId: activity.status.id,
                date: activity.date.split(' ')[0],
                time: activity.time
              });
            }}>Odustani</button>
          </>
        ) : (
          <>
            <p><strong>Naziv:</strong> {activity.name}</p>
            <p><strong>Status:</strong> {activity.status.name}</p>
            <p><strong>Datum:</strong> {new Date(activity.date).toLocaleDateString()}</p>
            <p><strong>Vrijeme:</strong> {activity.time}</p>
            <button onClick={() => setEditMode(true)}>Uredi</button>
          </>
        )}
      </div>

      <div className={styles.section}>
        <h3>Organizacija</h3>
        <p>{activity.organization.name} ({activity.organization.email})</p>
      </div>

      <div className={styles.section}>
        <h3>Lokacija</h3>
        {activity.location ? (
          editLocationMode ? (
            <>
              <input name="city" type="text" value={locationForm.city} onChange={handleLocationInputChange} placeholder="Grad" />
              <input name="country" type="text" value={locationForm.country} onChange={handleLocationInputChange} placeholder="Država" />
              <input name="address" type="text" value={locationForm.address} onChange={handleLocationInputChange} placeholder="Adresa" />
              <button onClick={handleSaveLocation}>Spremi lokaciju</button>
              <button onClick={() => {
                setEditLocationMode(false);
                setLocationForm({
                  city: activity.location.city,
                  country: activity.location.country,
                  address: activity.location.address
                });
              }}>Odustani</button>
            </>
          ) : (
            <>
              <p><strong>Grad:</strong> {activity.location.city}</p>
              <p><strong>Država:</strong> {activity.location.country}</p>
              <p><strong>Adresa:</strong> {activity.location.address}</p>
              <button onClick={() => setEditLocationMode(true)}>Uredi lokaciju</button>
            </>
          )
        ) : (
          <p>Nema lokacije dodijeljene.</p>
        )}
      </div>

      <div className={styles.section}>
        <h3>Volonteri</h3>
        <input
          type="text"
          placeholder="Pretraži volontere..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />

        <table>
          <thead>
            <tr>
              <th>Ime</th>
              <th>Prezime</th>
              <th>Email</th>
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            {filteredVolunteers.map((vol) => (
              <tr key={vol.id}>
                <td>{vol.firstName}</td>
                <td>{vol.lastName}</td>
                <td>{vol.email}</td>
                <td>
                  <button onClick={() => handleDeleteVolunteer(vol.id)}>Obriši</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <div>
          <h4>Dodaj postojećeg volontera</h4>
          <select value={selectedVolunteerId} onChange={(e) => setSelectedVolunteerId(e.target.value)}>
            <option value="">-- Odaberi volontera --</option>
            {availableVolunteers.map((vol) => (
              <option key={vol.id} value={vol.id}>
                {vol.name} {vol.surname}
              </option>
            ))}
          </select>
          <button onClick={handleAddVolunteer}>Dodaj volontera</button>
        </div>
      </div>
    </div>
  );
}

export default MasterDetailPage;
