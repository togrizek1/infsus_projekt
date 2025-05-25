import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import styles from './style.module.css';

function LandingPage() {
  const [activities, setActivities] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/activity/summary')
      .then(response => setActivities(response.data))
      .catch(error => console.error('Greška pri dohvaćanju aktivnosti:', error));
  }, []);

  return (
    <div className={styles.contentContainer}>
      <h2>Popis aktivnosti</h2>
      <ul className={styles.listContainer}>
        {activities.map(activity => (
          <li key={activity.id} className={styles.listMember}>
            <Link to={`/master-detail/${activity.id}`}>
              {activity.activityName} (organizacija: {activity.organizationName})
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default LandingPage;
