import { format, parseISO } from 'date-fns';

export const formatDate = (dateString) => format(parseISO(dateString), 'dd/MM/yyyy');