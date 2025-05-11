export const roles = [
    { value: 'CUSTOMER', label: 'Customer' },
    { value: 'AGENT', label: 'Delivery Agent' },
    { value: 'MANAGER', label: 'Manager' },
  ];

export const orderStatus = [
  { value: 'PLACED', label: 'Placed Orders' },
  { value: 'ACCEPTED', label: 'Active Orders' },
  { value: 'COMPLETED', label: 'Past Orders' },
]

export const managerOrderStatus = [
  { value: 'PLACED', label: 'Placed Orders' },
  { value: 'ACCEPTED', label: 'Active Orders' },
  { value: 'DECLINED', label: 'Declined Orders' },
  { value: 'COMPLETED', label: 'Completed Orders' },
]

export const agentOrderStatus = [
  { value: 'ACCEPTED', label: 'Active Orders' },
  { value: 'COMPLETED', label: 'Completed Orders' },
]