import React, { useEffect, useState } from 'react';
import styles from './codebook.module.css';
import axios from 'axios';

function CodebookPage() {
  const [statuses, setStatuses] = useState([]);
  const [newStatusName, setNewStatusName] = useState('');
  const [editingStatusId, setEditingStatusId] = useState(null);
  const [editingStatusName, setEditingStatusName] = useState('');

  const fetchStatuses = () => {
    axios.get('http://localhost:8080/api/activity-status')
      .then(response => {
        const sortedStatuses = response.data.sort((a, b) => a.id - b.id);
        setStatuses(sortedStatuses);
      })
      .catch(error => console.error('Error fetching statuses:', error));
  };

  useEffect(() => {
    fetchStatuses();
  }, []);

  const nameExists = (name, excludeId = null) => {
    return statuses.some(
      (s) => s.name.toLowerCase() === name.toLowerCase() && s.id !== excludeId
    );
  };

  const handleAddStatus = () => {
    if (!newStatusName.trim()) {
      alert('Naziv ne smije biti prazan.');
      return;
    }
    if (newStatusName.length > 50) {
      alert('Naziv ne smije imati više od 50 znakova.');
      return;
    }
    if (nameExists(newStatusName)) {
      alert('Status s tim nazivom već postoji.');
      return;
    }
    axios.post('http://localhost:8080/api/activity-status', { name: newStatusName })
      .then(() => {
        setNewStatusName('');
        fetchStatuses();
      })
      .catch(error => console.error('Error adding status:', error));
  };

  const handleEditClick = (status) => {
    setEditingStatusId(status.id);
    setEditingStatusName(status.name);
  };

  const handleSaveEdit = (id) => {
    if (!editingStatusName.trim()) {
      alert('Naziv ne smije biti prazan.');
      return;
    }
    if (editingStatusName.length > 50) {
      alert('Naziv ne smije imati više od 50 znakova.');
      return;
    }
    if (nameExists(editingStatusName, id)) {
      alert('Status s tim nazivom već postoji.');
      return;
    }
    axios.put(`http://localhost:8080/api/activity-status/${id}`, { name: editingStatusName })
      .then(() => {
        setEditingStatusId(null);
        setEditingStatusName('');
        fetchStatuses();
      })
      .catch(error => console.error('Error updating status:', error));
  };

  const handleDeleteStatus = (id) => {
    if (window.confirm('Jeste li sigurni da želite obrisati ovaj status?')) {
      axios.delete(`http://localhost:8080/api/activity-status/${id}`)
        .then(() => fetchStatuses())
        .catch(error => console.error('Error deleting status:', error));
    }
  };

  return (
    <div className={styles.container}>
      <h2>Šifrarnik statusa aktivnosti</h2>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Naziv</th>
            <th>Akcije</th>
          </tr>
        </thead>
        <tbody>
          {statuses.map((status) => (
            <tr key={status.id}>
              <td>{status.id}</td>
              <td>
                {editingStatusId === status.id ? (
                  <input
                    type="text"
                    value={editingStatusName}
                    onChange={(e) => setEditingStatusName(e.target.value)}
                  />
                ) : (
                  status.name
                )}
              </td>
              <td>
                {editingStatusId === status.id ? (
                  <>
                    <button onClick={() => handleSaveEdit(status.id)}>Spremi</button>
                    <button onClick={() => setEditingStatusId(null)}>Odustani</button>
                  </>
                ) : (
                  <>
                    <button onClick={() => handleEditClick(status)}>Uredi</button>
                    <button onClick={() => handleDeleteStatus(status.id)}>Obriši</button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <h4>Dodaj novi status</h4>
      <input
        type="text"
        value={newStatusName}
        onChange={(e) => setNewStatusName(e.target.value)}
        placeholder="Novi status"
      />
      <button onClick={handleAddStatus}>Dodaj</button>
    </div>
  );
}

export default CodebookPage;
